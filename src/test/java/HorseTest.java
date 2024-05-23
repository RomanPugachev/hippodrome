import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HorseTest {
    String validName = "HorseValidName";
    int validSpeed = 10;

    int validDistance = 10;
    @Test
    public void constructor_nullName_ThrowsExceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, validSpeed, validDistance));
    }
    @Test
    public void constructor_nullName_ThrowsExceptionWithMessageTest(){
        String exceptionMessage = null;
        try {
            new Horse(null, validSpeed, validDistance);
        } catch (IllegalArgumentException e) {exceptionMessage = e.getMessage();}
        assertEquals("Name cannot be null.", exceptionMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "", "        "})
    public void constructor_whiteSpaceName_ThrowsExceptionTest(String string) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(string, validSpeed, validDistance));
    }
    @ParameterizedTest
    @ValueSource(strings = {"   ", "", "        ", "\n\n\n", "\t\t\t"})
    public void constructor_whiteSpaceName_ThrowsExceptionWithMessageTest(String string) {
        String exceptionMessage = null;
        try {
            new Horse(string, validSpeed, validDistance);
        } catch (IllegalArgumentException e) {exceptionMessage = e.getMessage();}
        assertEquals("Name cannot be blank.", exceptionMessage);
    }
    @ParameterizedTest
    @ValueSource(ints = {-2, -3, -23})
    public void constructor_negativeSpeed_ThrowsExceptionTest(int speed) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(validName, speed, validDistance));
    }
    @ParameterizedTest
    @ValueSource(ints = {-2, -3, -23})
    public void constructor_negativeSpeed_ThrowsExceptionWithMessageTest(int speed) {
        String exceptionMessage = null;
        try {
            new Horse(validName, speed, validDistance);
        } catch (IllegalArgumentException e) {exceptionMessage = e.getMessage();}
        assertEquals("Speed cannot be negative.", exceptionMessage);
    }
    @ParameterizedTest
    @ValueSource(ints = {-2, -3, -23})
    public void constructor_negativeDistance_ThrowsExceptionTest(int invalidDistance) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(validName, validSpeed, invalidDistance));
    }
    @ParameterizedTest
    @ValueSource(ints = {-2, -3, -23})
    public void constructor_negativeDistance_ThrowsExceptionWithMessageTest(int invalidDistance) {
        String exceptionMessage = null;
        try {
            new Horse(validName, validSpeed, invalidDistance);
        } catch (IllegalArgumentException e) {exceptionMessage = e.getMessage();}
        assertEquals("Distance cannot be negative.", exceptionMessage);
    }
    @ParameterizedTest
    @ValueSource (strings = {"NameWithoutSpaces", "Name with spaces", "Name?!"})
    public void getNameReturnsCorrectName(String tempName){
        Horse horse = new Horse(tempName,validSpeed, validDistance);
        assertEquals(tempName, horse.getName());
    }
    @ParameterizedTest
    @ValueSource (ints = {0, 3, 100})
    public void getSpeedReturnsCorrectSpeed(int tempSpeed){
        Horse horse = new Horse(validName,tempSpeed, validDistance);
        assertEquals(tempSpeed, horse.getSpeed());
    }
    @ParameterizedTest
    @ValueSource (ints = {0, 3, 100})
    public void getDistance_constructorHasDistance_returnsCorrectDistance(int tempDistance){
        Horse horse = new Horse(validName,validSpeed, tempDistance);
        assertEquals(tempDistance, horse.getDistance());
    }
    @Test
    public void getDistance_noDistanceConstructor_returnsZero(){
        Horse horse = new Horse(validName,validSpeed);
        assertEquals(0, horse.getDistance());
    }
    @Test
    public void move_getRandomDoubleVerification(){
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){
            new Horse(validName,validSpeed,validDistance).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {e.printStackTrace();}
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "0, 0", "3, 0", "0, 3"})
    public void move_worksCorrect(int tempDistance, int tempSpeed){
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(1.0);
            Horse horse = new Horse(validName, tempSpeed, tempDistance);
            horse.move();
            assertEquals(tempDistance + tempSpeed * 1, horse.getDistance());
        } catch (Exception e) {e.printStackTrace();}
    }
}