package json;

import json.trace_span.Span;

import java.util.ArrayList;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 20:54
 */
public interface ConvertFromJson {
    ArrayList<Span> convert(String filePath);
}
