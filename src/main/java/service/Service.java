package service;

import dto.BaseSuccessResponse;
import dto.ChangeStatusTodoDto;
import dto.CreateToDoDto;
import dto.GetNewsDto;
import entity.ToDoEntity;
import exception.LengthLimit;
import repository.ToDoDB;

import java.awt.print.Pageable;
import java.util.Comparator;
import java.util.List;

public class Service {
    private ToDoDB toDoDB = new ToDoDB();

    public ToDoEntity create(CreateToDoDto toDo) {
        ToDoEntity toDoEntity = new ToDoEntity();
        try {
            toDoEntity.setText(toDo.getText());
        } catch (LengthLimit e) {
            throw new RuntimeException(e);
        }
        toDoDB.insert(toDoEntity);
        return toDoEntity;
    }

    public BaseSuccessResponse deleteAllReady() {

        toDoDB.deleteAllReady();
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse deleteById(Long id) {
        toDoDB.deleteById(id);

        return new BaseSuccessResponse();
    }

    //
    public BaseSuccessResponse changeStatusTodoDto(ChangeStatusTodoDto change) {

        toDoDB.changedStatus(change.isStatus());

        return new BaseSuccessResponse();
    }

    public void changeCurrentText(CreateToDoDto objectMapper, Long id) {
        toDoDB.changeText(objectMapper.getText(), id);
    }

    public void changeCurrentStatus(ChangeStatusTodoDto objectMapper, Long id) {
        toDoDB.changeStatus(objectMapper.isStatus(), id);
    }

    public GetNewsDto<ToDoEntity> getPaginated(int page, int perPage) {
        List<ToDoEntity> list = toDoDB.select(page, perPage);
        GetNewsDto<ToDoEntity> content = new GetNewsDto<>();
        content.setContent(list)
                .setNotReady((int) list.stream().filter(p -> !p.getStatus()).count())
                .setNumberOfElements(10)
                .setReady(5);
        return content;
    }

    public GetNewsDto<ToDoEntity> getPaginated(int page, int perPage, Boolean status) {
        List<ToDoEntity> list = toDoDB.select(page, perPage, status);
        GetNewsDto<ToDoEntity> content = new GetNewsDto<>();
        content.setContent(list)
                .setNotReady((int) list.stream().filter(p -> !p.getStatus()).count())
                .setNumberOfElements(10)
                .setReady(5);
        return content;
    }

}
