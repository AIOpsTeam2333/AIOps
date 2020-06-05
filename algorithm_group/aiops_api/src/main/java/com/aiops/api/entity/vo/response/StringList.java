package com.aiops.api.entity.vo.response;


import java.util.ArrayList;

/**
 * @author Shuaiyu Yao
 * @create 2020-06-02 23:07
 **/
public class StringList extends ArrayList<String> {

    public StringList(){
        super();
    }

    public StringList(ArrayList<String> newArrayList) {
        super(newArrayList);
    }
}
