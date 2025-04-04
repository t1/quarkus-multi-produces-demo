package com.github.t1;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static com.github.t1.YamlMessageBodyAdapter.APPLICATION_YAML;
import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

@Provider
@Produces(APPLICATION_YAML)
@Consumes(APPLICATION_YAML)
public class YamlMessageBodyAdapter implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
    public static final String APPLICATION_YAML = "application/yaml";

    @SuppressWarnings("unused")
    public static final MediaType APPLICATION_YAML_TYPE = MediaType.valueOf(APPLICATION_YAML);


    public YamlMessageBodyAdapter() {
        var dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(BLOCK);

        var loadingOptions = new LoaderOptions();
        loadingOptions.setTagInspector(tag -> true); // Don't do this with a real service!!!
        // it's unsafe to allow using arbitrary class names

        this.yaml = new Yaml(new Constructor(loadingOptions), new Representer(dumperOptions), dumperOptions);
    }

    private final Yaml yaml;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(
            Object o,
            Class<?> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream) {
        yaml.dump(o, new OutputStreamWriter(entityStream));
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(
            Class<Object> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders,
            InputStream entityStream) {
        return yaml.load(entityStream);
    }
}
