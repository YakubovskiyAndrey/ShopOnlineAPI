package ua.yakubovskiy.rest.service;

import ua.yakubovskiy.rest.dto.SmartphoneDetails;
import ua.yakubovskiy.rest.dto.SmartphoneQueryDto;
import ua.yakubovskiy.rest.dto.SmartphoneSave;
import java.util.List;

public interface SmartphoneService {

    int create(SmartphoneSave smartphoneSave);

    SmartphoneDetails getById(int id);

    List<SmartphoneDetails> getAll();

    List<SmartphoneDetails> search(SmartphoneQueryDto query);

    void update(int id, SmartphoneSave smartphoneSave);

    void delete(int id);
}
