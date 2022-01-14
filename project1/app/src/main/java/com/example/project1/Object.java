package com.example.project1;

import java.util.List;
//class that used to convert jason to java object
public class Object {
    private String next_page_token;
    private List<Result> results;
    public Object( String next_page_token, List<Result> results){
        this.next_page_token = next_page_token;
        this.results = results;
    }
    public String getNext_page_token() {
        return next_page_token;
    }
    public List<Result> getResultsArray() {
        return results;
    }
    public Result getResults(int position) {
        return results.get(position);
    }
    public int getResultCount(){
        return results.size();
    }
}
