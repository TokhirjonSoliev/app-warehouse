package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurementService(Measurement measurement){
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName){
            return new Result("Bunday olchov birligi mavjud", false);
        }
        measurementRepository.save(measurement);
        return new Result("Muvaffaqiyatli qoshildi", true);
    }

    public List<Measurement> getMeasurementList(){
        List<Measurement> measurementList = measurementRepository.findAll();
        return measurementList;
    }

    public Measurement getMeasurement(Integer measurementId){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
        return optionalMeasurement.orElse(null);
    }

    public Result editMeasurement(Integer measurementId, Measurement measurement){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
        if (optionalMeasurement.isPresent()){
            Measurement measurement1 = optionalMeasurement.get();
            measurement1.setName(measurement.getName());
            measurementRepository.save(measurement1);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday olchov mavjud emas", false);
    }

    public Result deleteMeasurement(Integer measurementId){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
        if (optionalMeasurement.isPresent()){
            measurementRepository.deleteById(measurementId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday olchov mavjud emas", false);
    }
}
