package fapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface HashTagService {
    String getTextWithHashTags(String text);
}
