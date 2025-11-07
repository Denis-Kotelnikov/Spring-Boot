package ru.Kotelnikov.SecondApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Response;
import ru.Kotelnikov.SecondApp.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemResponseService")
@Slf4j
public class ModifySystemResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        log.info("Modifying response systemTime: {}", response);
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("Modified response systemTime: {}", response);
        return response;
    }
}
