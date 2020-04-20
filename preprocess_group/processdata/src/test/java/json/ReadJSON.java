package json;

import json.trace_span.Span;
import org.junit.Test;

import java.util.ArrayList;

public class ReadJSON {
    @Test
    public void readJson() {
        ConvertFromJson convertFromJson = new ConvertFromJsonImpl();
        String filepath = ReadJSON.class.getClassLoader().getResource("test/Trace.json").getPath();
        ArrayList<Span> spans = convertFromJson.convert(filepath);
        System.out.println(spans.size());
    }
}
