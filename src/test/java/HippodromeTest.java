import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    public void nullConstructorArgument(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }
    @Test
    public void emptyListConstructorArgument(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }
    @Test
    public void getHorses_someList_returnsListFromConstructor(){
        List<Horse> horseArrayList = new ArrayList<>(30);
        for (int i = 0; i < 30; i++){
            horseArrayList.add(i, new Horse(String.format("Horse number %d", i), i, i));
        }
        Hippodrome testHippodrome = new Hippodrome(horseArrayList);
        assertArrayEquals(horseArrayList.toArray(), testHippodrome.getHorses().toArray());
    }
    @Test
    public void moveExecutesCorrectly(){
        List<Horse> horseList = new ArrayList<>(50);
        for (int i = 0; i < 50; i++){
            horseList.add(i, mock(Horse.class));
        }
        Hippodrome testHippodrome = new Hippodrome(horseList);
        testHippodrome.move();
        for (int i = 0; i < 50; i++){
            verify(horseList.get(i)).move();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10})
    public void getWinner_ReturnsWinner(int size){
        Random random = new Random();
        int randomIndexOfMax = random.nextInt(size);
        List<Horse> horseList = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            if (i == randomIndexOfMax){
                horseList.add(i, new Horse(String.format("Horse number %d", i), i, Integer.MAX_VALUE - 100));
                continue;
            }
            horseList.add(i, new Horse(String.format("Horse number %d", i), i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);


        assertEquals(horseList.get(randomIndexOfMax).getDistance(), hippodrome.getWinner().getDistance());
    }
}