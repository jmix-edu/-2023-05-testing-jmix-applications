package com.company.jmixpm

import com.company.jmixpm.entity.Task
import com.company.jmixpm.entity.User
import com.company.jmixpm.screen.task.TaskEdit
import io.jmix.core.DataManager
import io.jmix.core.ValueLoadContext
import io.jmix.core.entity.KeyValueEntity
import io.jmix.core.security.SystemAuthenticator
import io.jmix.ui.ScreenBuilders
import io.jmix.ui.testassistspock.spec.ScreenSpecification
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

@ContextConfiguration(classes = JmixPmApplication)
@TestPropertySource("classpath:application.properties")
class TaskEditTest extends ScreenSpecification {

    @Autowired
    SystemAuthenticator authenticator

    @SpringBean
    DataManager dataManager = Stub()

    @Autowired
    ScreenBuilders screenBuilders

    @Override
    protected void setupAuthentication() {
        authenticator.begin()
    }

    @Override
    protected void cleanupAuthentication() {
        authenticator.end()
    }

    protected List<KeyValueEntity> generateUserEstimationEfforts() {
        def user1 = metadata.create(User)
        user1.username = "user1"

        def user2 = metadata.create(User)
        user2.username = "user2"

        def entity1 = metadata.create(KeyValueEntity)
        entity1.setValue("user", user1)
        entity1.setValue("estimatedEfforts", 1L)

        def entity2 = metadata.create(KeyValueEntity)
        entity2.setValue("user", user2)
        entity2.setValue("estimatedEfforts", 2L)

        [entity1, entity2]
    }

    def "Check the computation of the least busy user"() {
        showTestMainScreen()

        given: "Test data"
        def testEntities = generateUserEstimationEfforts()

        and: "Stub for DataManager"
        dataManager.loadValues(_ as ValueLoadContext) >> testEntities

        when: "Open TaskEdit screen"
        def taskEditScreen = screenBuilders.editor(Task, screens.openedScreens.rootScreen)
                .withScreenClass(TaskEdit)
                .newEntity()
                .show()

        then: "Initialized Task instance should have assigned user"
        taskEditScreen.editedEntity.assignee == testEntities.get(0).getValue("user")
    }
}
