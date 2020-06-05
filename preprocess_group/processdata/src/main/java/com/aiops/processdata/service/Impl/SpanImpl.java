package com.aiops.processdata.service.Impl;

import com.aiops.processdata.dao.SpanRepository;
import com.aiops.processdata.entity.span.Ref_Info;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.SpanPO;
import com.aiops.processdata.po.span.TraceSpanPO;
import com.aiops.processdata.service.Span;
import com.aiops.processdata.service.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 22:22
 */
@Service
public class SpanImpl implements Span {

    @Autowired
    private SpanRepository spanRepository;
    @Autowired
    private Trace trace;

    @Override
    public List<SpanPO> save(Span_Data span_data) {
        List<Span_Info> span_infoList = span_data.getData().getQueryTrace().getSpans();
        List<SpanPO> spanPOList = new ArrayList<>();
        List<Span_Info> refOrParent_span_infoList = new ArrayList<>();
        for (Span_Info span_info : span_infoList) {

            //先插入父span,将所有含有引用的span信息和子span信息存列表
            if (span_info.getParentSpanId() != -1 || span_info.getRefs().size() != 0) {
                refOrParent_span_infoList.add(span_info);
                continue;
            }
            //判断已存在
            String pre_segment_id = span_info.getTraceId() + "/" + span_info.getSegmentId();
            String pre_id = pre_segment_id + "/" + span_info.getSpanId();
            SpanPO spanPO = spanRepository.findSpanPOById(pre_id);
            if (spanPO != null) {
                System.out.println("插入父span: " + pre_id + " 已存在，跳过");
                //spanPOList.add(spanPO);
                continue;
            }
            if (!judgeExistSegment(pre_segment_id)) {
                System.out.println("插入父span: " + pre_id + " 坏节点，节点所属segment未注册");
                continue;
            }
            spanPO = spanRepository.insertSpan(span_info);
            System.out.println("插入父span：" + pre_id + " 成功！");
            spanPOList.add(spanPO);
        }

        //再插入引用span和子span
        for (Span_Info span_info : refOrParent_span_infoList) {
            //判断已存在
            String pre_segment_id = span_info.getTraceId() + "/" + span_info.getSegmentId();
            String pre_id = pre_segment_id + "/" + span_info.getSpanId();

            SpanPO spanPO = spanRepository.findSpanPOById(pre_id);
            if (spanPO != null) {
                //spanPOList.add(spanPO);
                System.out.println("插入子span: " + pre_id + " 已存在，跳过");
                continue;//已存在 跳过
            }
            if (!judgeExistSegment(pre_segment_id)) {
                System.out.println("插入子span: " + pre_id + " 坏节点，节点所属segment未注册");
                continue;
            }
            //验证是否正常节点
            if (!judgeRightSpan(span_info)) {
                continue;//坏节点跳过
            }
            spanPO = spanRepository.insertSpan(span_info);
            System.out.println("插入子span：" + pre_id + " 成功！");
            spanPOList.add(spanPO);
        }
        return spanPOList;
    }

    @Override
    public Integer findById(String pre_id) {
        TraceSpanPO traceSpanPO = spanRepository.findTraceSpanById(pre_id);
        return traceSpanPO == null ? -1 : traceSpanPO.getId();
    }

    /**
     * 判断节点是否正确，若是坏节点则返回false
     * 坏节点包括：
     * 1.父节点不存在
     * 2.ref节点不存在
     *
     * @param span_info
     * @return
     */
    private boolean judgeRightSpan(Span_Info span_info) {
        //父节点全名
        String pre_id = span_info.getTraceId() + "/" + span_info.getSegmentId()
                + "/" + span_info.getParentSpanId();
        if (span_info.getParentSpanId() != -1) {
            if (findById(pre_id) == -1) {
                System.out.println("插入子span: " + pre_id + " 坏节点，节点所属父span未注册");
                return false;
            }
        }
        for (Ref_Info ref_info : span_info.getRefs()) {
            //引用到的节点
            if (ref_info.getParentSpanId() != -1) {
                String ref_pre_id = ref_info.getTraceId() + "/"
                        + ref_info.getParentSegmentId() + "/" + ref_info.getParentSpanId();
                if (findById(ref_pre_id) == -1) {
                    System.out.println("插入子span: " + pre_id + " 坏节点，引用span未注册");
                    return false;//存在引用节点不存在
                }
            }
        }
        return true;
    }

    /**
     * 判断该节点所属的segment是否在trace表中注册
     *
     * @param pre_segment_id
     * @return
     */
    private boolean judgeExistSegment(String pre_segment_id) {
        Integer segmentId = trace.findSegmentById(pre_segment_id);
        return segmentId == -1 ? false : true;
    }
}
