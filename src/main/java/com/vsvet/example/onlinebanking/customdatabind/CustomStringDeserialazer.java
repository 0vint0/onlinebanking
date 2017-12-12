package com.vsvet.example.onlinebanking.customdatabind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.google.common.base.CharMatcher;

import java.io.IOException;

public class CustomStringDeserialazer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String nodeValue = StringDeserializer.instance.deserialize(p, ctxt);
        nodeValue = CharMatcher.WHITESPACE.trimFrom(nodeValue);
        return nodeValue;
    }

}
