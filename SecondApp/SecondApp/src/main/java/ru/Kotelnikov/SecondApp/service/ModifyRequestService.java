package ru.Kotelnikov.SecondApp.service;

import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Request;
import ru.Kotelnikov.SecondApp.model.Response;

@Service
public interface ModifyRequestService {
    Response modify(Request request);
}
