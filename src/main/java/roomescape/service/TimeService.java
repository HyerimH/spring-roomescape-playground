package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeDAO timeDAO;

    public TimeResponse createTime(TimeRequest request){
        Time time = request.toTime();
        Long newId=timeDAO.insertTime(time);
        Time savedTime=time.withId(newId);
        return new TimeResponse(
                savedTime.getId(),
                savedTime.getTime()
        );
    }

    public List<Time> getAllTimes(){
        return timeDAO.findAllTimes();
    }

    public Time getTimeById(Long id){
        return timeDAO.findTimeById(id);
    }

    public void deleteTime(Long id) {
        timeDAO.deleteTime(id);
    }
}
