package fapi.service;

import fapi.constants.URL;
import fapi.exception.ResponseErrorHandlerIml;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashTagServiceImp implements HashTagService {

    @Override
    public String getTextWithHashTags(String text){
        StringBuffer stringBuffer = new StringBuffer(text);

        Pattern myPattern = Pattern.compile("(#[A-Za-z0-9]+)");
        Matcher matcher = myPattern.matcher(text);
        int length = ("<a href=\""+ URL.FRONT_POSTS_HASHTAG +"\">"+"</a>").length();
        int increase = 0;
        while (matcher.find()) {
            int startIndex = matcher.start()+increase;
            int endIndex = matcher.end()+increase;
            stringBuffer.replace(startIndex,endIndex,"<a href=\""+ URL.FRONT_POSTS_HASHTAG+matcher.group(0).substring(1)+"\">" +matcher.group(0)+"</a>");
            increase +=length+matcher.group(0).substring(1).length();
        }
        return stringBuffer.toString();
    }
}
