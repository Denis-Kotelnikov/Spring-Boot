package ru.Kotelnikov.SecondApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Response;

import java.util.UUID;

@Service
@Qualifier("ModifyOperationUidResponseService")
@Slf4j
public class ModifyOperationUidResponseService
    implements ModifyResponseService {
        @Override
        public Response modify(Response response) {
            log.info("Modifying response operationUid: {}", response);
            UUID uuid = UUID.randomUUID();
            response.setOperationUid(uuid.toString());
            log.info("Modified response operationUid: {}", response);
            return response;
        }
    }

