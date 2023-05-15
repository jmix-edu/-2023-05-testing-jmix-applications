package com.company.jmixpm.app;

import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.ValueLoadContext.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Comparator.*;

@Service
public class TaskService {

    @Autowired
    private DataManager dataManager;

    public User findLeastBusyUser() {
        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(new Query(
                        "select u, sum(t.estimatedEfforts) " +
                                "from User u left outer join Task_ t " +
                                "on u = t.assignee " +
                                "group by u order by sum(t.estimatedEfforts)"))
                .addProperty("user")
                .addProperty("estimatedEfforts");

        return dataManager.loadValues(context)
                .stream()
                .sorted(comparing(entity -> (Long) entity.getValue("estimatedEfforts"), nullsFirst(naturalOrder())))
                .map(e -> e.<User>getValue("user"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}