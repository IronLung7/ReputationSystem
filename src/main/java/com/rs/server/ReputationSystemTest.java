package com.rs.server;

import com.rs.data.repository.Repository;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by leo on 15/3/13.
 */
public class ReputationSystemTest {

    private ConfigurableApplicationContext context;

    public ReputationSystemTest(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public void run() {
        Repository repository = new Repository(context);
        //Repository.eraseAllData();
        Repository.insertTestData();

        System.out.println(Repository.ratingRepository.findByItem(Repository.itemRepository.findById(15l)).get(0).getId());
    }

    public void reputationRun(){

    }
}
