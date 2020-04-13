package com.AIOps.Leonarda;

import com.AIOps.Leonarda.service.GraphqlCollection;
import com.AIOps.Leonarda.service.impl.GraphqlCollectionImpl;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        GraphqlCollection graphqlCollection=new GraphqlCollectionImpl();
        graphqlCollection.collectTracesAndSpans();
        graphqlCollection.collectServiceAndInstance();
        graphqlCollection.collectEndpoint();
    }
}
