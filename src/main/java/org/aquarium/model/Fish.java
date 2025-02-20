package org.aquarium.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.aquarium.enums.FishState;
import org.aquarium.enums.GenderEnum;
import org.aquarium.util.PrintUtils;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.aquarium.enums.FishState.*;
import static org.aquarium.enums.GenderEnum.MALE;
import static org.aquarium.util.FishUtils.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class Fish extends Thread {

    public static final Set<Fish> FISH_LIST = ConcurrentHashMap.newKeySet();
    private static final Lock lock = new ReentrantLock();
    private static final int THREAD_SLEEP_TIME = 100;

    private static final Aquarium aquarium = new Aquarium();

    private GenderEnum gender;
    private Integer maxAge;

    private Integer age;
    private FishState fishState;

    @SneakyThrows
    @Override
    public void run() {

        for (int i = 1; i <= maxAge; i++) {

            this.age = i;

            Thread.sleep(THREAD_SLEEP_TIME);

            if (isChild(this)) changeState(this, CHILD);

            lock.lock();
            try {
                if (isMature(this) && !isMarried(this)) {

                    changeState(this, MATURE);

                    Optional<Fish> optionalFish = getHusbandFish();

                    if (optionalFish.isPresent()) {

                        Fish fish = optionalFish.get();

                        changeState(this, MARRIED);
                        changeState(fish, MARRIED);

                        if (this.gender == MALE) addBabyFish(this, fish);
                        else addBabyFish(fish, this);
                    }
                }
            } finally {
                lock.unlock();
            }

            if (isAdult(this)) changeState(this, ADULT);

            if (isTimeToDie(this)) toKill(this);
        }

    }

    public static Wedding doWedding() {

        int maleCount = 0;
        int femaleCount = 0;

        GenderEnum gender;

        for (int i = 0; i < fishesCount(); i++) {

            gender = getGenderRandom();

            if (gender.equals(MALE)) maleCount++;
            else femaleCount++;

            if (FISH_LIST.size() >= aquarium.getMaxFishesCount())
                break;
//                System.exit(1);

            Fish fish = new Fish(gender, maxAge());
            FISH_LIST.add(fish);
            fish.start();
        }
        return new Wedding(maleCount, femaleCount);
    }

    public synchronized Optional<Fish> getHusbandFish() {

        for (Fish fish : FISH_LIST)
            if (this.gender != fish.getGender() && isMature(fish) && fish.getFishState() != FishState.MARRIED)
                return Optional.of(fish);

        return Optional.empty();
    }

    public static void startTheAquarium() {
        Wedding result = doWedding();
        PrintUtils.startPrint(result);
    }

    public void addBabyFish(Fish father, Fish mother) {
        Wedding result = doWedding();
        PrintUtils.printEvent(father, mother, result.maleCount(), result.femaleCount());
    }

    public Fish(GenderEnum gender, int maxAge) {
        this.fishState = FishState.BORN;
        this.age = 0;
        this.gender = gender;
        this.maxAge = maxAge;
    }
}
