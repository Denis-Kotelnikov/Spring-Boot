package ru.kotelnikov.MeFirstApp.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World")String name) {
        return String.format("Hello %s!", name);
    }
    // Поле для хранения массива строк, изначально null, создается при первом вызове
    private List<String> arrayList = null;
    // Поле для хранения отображения (ключ-значение), изначально null
    private Map<Integer, String> hashMap = null;

    // 1. Метод updateArrayList
    // Обработчик GET-запроса по URL "/update-array"
    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam("s") String s) {
        // Если arrayList еще не создан, создаем новую пустую коллекцию
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        // Добавляем полученную строку s в список
        arrayList.add(s);
        // Возвращаем сообщение об успешном добавлении элемента
        return "Элемент добавлен в ArrayList.";
    }

    // Обработчик GET-запроса по URL "/show-array"
    @GetMapping("/show-array")
    public List<String> showArrayList() {
        // Если arrayList еще не создан, возвращаем пустой список
        if (arrayList == null) {
            return new ArrayList<>();
        }
        // Возвращаем текущие элементы arrayList
        return arrayList;
    }

    // Обработчик GET-запроса по URL "/update-map"
    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam("s") String s) {
        // Если hashMap еще не создан, создаем новую пустую карту
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        // Генерируем ключ для записи: текущий размер карты + 1
        int key = (hashMap.size() + 1);
        // Добавляем новую пару ключ-значение в карту
        hashMap.put(key, s);
        // Возвращаем сообщение об успешном добавлении
        return "Элемент добавлен в HashMap.";
    }

    // Обработчик GET-запроса по URL "/show-map"
    @GetMapping("/show-map")
    public Map<Integer, String> showHashMap() {
        // Если hashMap еще не создан, возвращаем пустую карту
        if (hashMap == null) {
            return new HashMap<>();
        }
        // Возвращаем текущие элементы hashMap
        return hashMap;
    }

    // Обработчик GET-запроса по URL "/show-all-lenght"
    @GetMapping("/show-all-lenght")
    public String showAllLength() {
        // Получаем размер arrayList, если он создан, иначе 0
        int arraySize = (arrayList != null) ? arrayList.size() : 0;
        // Получаем размер hashMap, если он создан, иначе 0
        int mapSize = (hashMap != null) ? hashMap.size() : 0;
        // Формируем строку с количеством элементов в обеих коллекциях
        return String.format("Длина ArrayList: %d, Длина HashMap: %d", arraySize, mapSize);
    }

}
