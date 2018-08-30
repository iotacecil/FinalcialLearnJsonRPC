package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
    static Logger log = LoggerFactory.getLogger(RestUtil.class);

    public static HttpEntity<String> makePostJSONEntiry(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> formEntity = new HttpEntity<String>(
                JsonUtil.toJson(param), headers);
        log.info("rest-post-json-请求参数:{}", formEntity.toString());
        return formEntity;
    }
    public static <T> T postJSON(RestTemplate restTemplate, String url, Object param, Class<T> responseType) {
        HttpEntity<String> formEntity = makePostJSONEntiry(param);
        T result = restTemplate.postForObject(url, formEntity, responseType);
        log.info("rest-post-json 响应信息:{}", JsonUtil.toJson(result));
        return result;
    }
}
