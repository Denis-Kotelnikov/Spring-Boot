package ru.Kotelnikov.SecondApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.Kotelnikov.SecondApp.model.Request;
import ru.Kotelnikov.SecondApp.model.Response;


@Service
@Slf4j
public class ModifySourceAndSendRequestService implements ModifyRequestService {
    @Override
    public Response modify(Request request) {
        log.info("Modifying source in request: {}", request);
        request.setSource("modified_from_service1");  // Изменение поля source
        log.info("Modified source in request: {}", request);

        // Отправка запроса в Сервис 2
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Request> entity = new HttpEntity<>(request);
        Response service2Response = restTemplate.exchange(
                "http://localhost:8084/feedback",  // URL Сервиса 2 (измените порт, если нужно)
                HttpMethod.POST,
                entity,
                Response.class
        ).getBody();

        if (service2Response == null) {
            throw new RuntimeException("No response from Service 2");
        }

        log.info("Received response from Service 2: {}", service2Response);
        return service2Response;
    }
}
