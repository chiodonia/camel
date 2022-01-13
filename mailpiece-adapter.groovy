restConfiguration()
    .component("http")
    .host("mailpiece-api")
    .port(8080)
    .bindingMode(org.apache.camel.model.rest.RestBindingMode.json);

class State {
  String state;
}

from("timer:tick?period=5000")
    .to("rest:get:/api/")
    .split().jsonpath("\$[*].id")
        .setHeader("id", simple("\${body}"))
        .enrich()
            .simple("rest:get:/api/state/\${header.id}")
            .unmarshal(new org.apache.camel.component.jackson.JacksonDataFormat(State.class))
            .setBody(simple('{"id": ${header.id}, "state": "${body.state}"}'))
            .setHeader(org.apache.camel.component.kafka.KafkaConstants.KEY, header("id"))
            .to("kafka:logistics.Mailpiece-state?brokers=kafka-kafka-bootstrap:9092")
            .to("log:DEBUG?showBody=true&showHeaders=true");
