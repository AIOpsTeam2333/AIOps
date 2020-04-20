package json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import json.trace_span.Log;
import json.trace_span.Ref;
import json.trace_span.Span;
import json.trace_span.Tag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/3/27 13:48
 */
public class ConvertFromJsonImpl implements ConvertFromJson {

    @Override
    public ArrayList<Span> convert(String filePath) {
        JsonParser parser = new JsonParser();
        ArrayList<Span> spans = new ArrayList<>();
        try {
            JsonArray array = (JsonArray) parser.parse(new FileReader(
                    filePath));  //创建JsonObject对象

            for (int i = 0; i < array.size(); i++) {
                JsonArray subArray = array.get(i).getAsJsonArray();
                for (int j = 0; j < subArray.size(); j++) {
                    JsonObject subObject = subArray.get(j).getAsJsonObject();
                    Span span = new Span();

                    span.setTraceId(subObject.get("traceId").getAsString());
                    span.setServiceCode(subObject.get("serviceCode").getAsString());
                    span.setEndpointName(subObject.get("endpointName").getAsString());
                    span.setType(subObject.get("type").getAsString());
                    span.setParentSpanId(subObject.get("parentSpanId").getAsInt());
                    span.setLayer(subObject.get("layer").getAsString());

                    //添加tags
                    JsonArray tagArray = subObject.get("tags").getAsJsonArray();    //得到为json的数组
                    ArrayList<Tag> tags = new ArrayList<>();
                    for (int k = 0; k < tagArray.size(); k++) {
                        JsonObject tag_object = tagArray.get(k).getAsJsonObject();
                        Tag tag = new Tag();
                        tag.setValue(tag_object.get("value").getAsString());
                        tag.setKey(tag_object.get("key").getAsString());
                        tags.add(tag);
                    }
                    span.setTags(tags);

                    span.setSpanId(subObject.get("spanId").getAsInt());
                    span.setComponent(subObject.get("component").getAsString());
                    span.setError(subObject.get("isError").getAsBoolean());

                    //添加refs
                    JsonArray refArray = subObject.get("refs").getAsJsonArray();    //得到为json的数组
                    ArrayList<Ref> refs = new ArrayList<>();
                    for (int k = 0; k < refArray.size(); k++) {
                        JsonObject ref_object = refArray.get(k).getAsJsonObject();
                        Ref ref = new Ref();
                        ref.setTraceId(ref_object.get("traceId").getAsString());
                        ref.setParentSegmentId(ref_object.get("parentSegmentId").getAsString());
                        ref.setType(ref_object.get("type").getAsString());
                        ref.setParentSpanId(ref_object.get("parentSpanId").getAsInt());
                        refs.add(ref);
                    }
                    span.setRefs(refs);

                    span.setPeer(subObject.get("peer").getAsString());
                    span.setSegmentId(subObject.get("segmentId").getAsString());
                    span.setStartTime(subObject.get("startTime").getAsLong());
                    span.setEndTime(subObject.get("endTime").getAsLong());

                    //添加logs
                    JsonArray logArray = subObject.get("logs").getAsJsonArray();
                    ArrayList<Log> logs = new ArrayList<Log>();
                    for (int k = 0; k < logArray.size(); k++) {
                        JsonObject log_object = logArray.get(k).getAsJsonObject();
                        Log log = new Log();
                        //添加 待修改
                        logs.add(log);
                    }
                    span.setLogs(logs);
                    spans.add(span);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("filepath is unright");
            e.printStackTrace();
        }
        return spans;
    }
}
