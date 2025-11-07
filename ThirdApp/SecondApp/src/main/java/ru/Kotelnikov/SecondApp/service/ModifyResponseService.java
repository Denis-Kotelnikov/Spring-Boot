package ru.Kotelnikov.SecondApp.service;

import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
