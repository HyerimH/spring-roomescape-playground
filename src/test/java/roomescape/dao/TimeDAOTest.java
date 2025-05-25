package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Time;
import roomescape.exception.NotFoundException;

@JdbcTest
@Import(TimeDAO.class)
class TimeDAOTest {

    @Autowired
    private TimeDAO timeDAO;

    private Time insertedTime;

    @BeforeEach
    void setUp() {
        Time time = new Time(LocalTime.parse("10:00"));
        Long id = timeDAO.insertTime(time);
        insertedTime = time.withId(id);
    }

    @Test
    void insertTime() {
        assertThat(timeDAO.findAllTimes()).contains(insertedTime);
    }

    @Test
    void findAllTimes() {
        assertThat(timeDAO.findAllTimes()).contains(insertedTime);
    }

    @Test
    void findTimeById() {
        Time found = timeDAO.findTimeById(insertedTime.getId());
        assertThat(found).isEqualTo(insertedTime);
    }

    @Test
    void deleteTimeById() {
        timeDAO.deleteTimeById(insertedTime.getId());
        assertThatThrownBy(() -> timeDAO.findTimeById(insertedTime.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
