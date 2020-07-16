package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Shift> getAll() {
        return shiftRepository.findAll();
    }

    public void registerNewShift(String name, String startTime, String endTime,
                                 String startDate, String endDate){
        Shift newShift = Shift.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        shiftRepository.save(newShift);
    }

    public void assignShiftToUser(String shiftName, String userName){
        ShifterUser user = userRepository.findShifterUserByUsername(userName);
        Shift shift = shiftRepository.findShiftByName(shiftName);
        user.addToShifts(shift);
    }

    public void saveShits(List<Shift> shifts) {
        shiftRepository.saveAll(shifts);
    }
}
