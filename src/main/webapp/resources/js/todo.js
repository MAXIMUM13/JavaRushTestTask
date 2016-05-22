"use strict";

var page = 1;
var count;
var total;

var editMode = false;
var addMode = false;
var selectMode = true;
var firstText;

$(function() {
    ajax(location.href + "count/" + category(), null, function(data) {
        count = parseInt(data);
        var rows = rowCount();
        total = Math.ceil(count / rows);
        $(".page-select").bootpag({
            total: total,
            maxVisible: 10
        }).on("page", function(e, num) {
            page = num;
            load();
        });
        load();
        $("#categories").change(load);
        $("#sort-by").change(load);
        $("#sort-order").change(load);
        $("#row-count").change(load);
        $("#add-todo").click(addTodo);
        $("#delete-todos").click(deleteTodos);
    });
});

function category() {
    return $("#categories")[0].value;
}

function sortBy() {
    return $("#sort-by")[0].value;
}

function order() {
    return $("#sort-order")[0].value;
}

function rowCount() {
    return $("#row-count")[0].value;
}

function load() {
    ajax(location.href + "count/" + category(), null, function(data) {
        count = parseInt(data);
        var rows = rowCount();
        total = Math.ceil(count / rows);
        if (page > total) {
            page = total;
        }

        $(".page-select").bootpag({
            total: total,
            maxVisible: 10,
            page: page
        });
        loadData(page, category(), sortBy(), order(), rowCount());
    });
}

function loadData(num, category, sortBy, order, count) {
    if (!num) {
        num = page;
    }
    if (!category) {
        category = "all";
    }
    if (!sortBy) {
        sortBy = "id";
    }
    if (!order) {
        order = "asc";
    }
    if (!count) {
        count = rowCount();
    }

    var url = location.href + "todos.json";
    var table = $("#todos");
    ajax(url, {
        page: num,
        category: category,
        sortBy: sortBy,
        order: order,
        count: count
    }, function(data) {
        $(".table-row").remove();
        for (var i in data) {
            var id = data[i].id;
            var description = data[i].description;
            var done = data[i].done;
            var row = tableRow("table-row", id, description, done);
            row.appendTo(table);
        }
        $(".description").dblclick(convertTextInInputField);
        $(".done").click(changeDoneField);
        $(".description").click(selectRow);
    });
}

function addTodo() {
    if (!editMode && !addMode) {
        addMode = true;
        var tr = $("<tr class='tr'>"
                + "<td>#</td>" + "<td>"
                + "<textarea class='new-todo-input'></textarea>"
                + "<input class='add-todo-button' type='button' value='Save'/>"
                + "<input class='cancel-add-todo-button' type='button' value='Cancel'/>"
                + "</td>"
                + "<td></td>"
                + "</tr>");
        var header = $("#header");
        header.after(tr);
        $(".add-todo-button").click(add);
        $(".cancel-add-todo-button").click(cancelAddition);
    }
}

function deleteTodos() {
    var ids = [];
    $(".table-row.selected").each(function(i, el) {
        ids[ids.length] = $(el).children(".id").text();
    });
    ajax(location.href + "delete.json", {
        ids: ids.toString()
    }, function() {
        cancelSelection();
        load();
    });
}

/**
 * Создать и вернуть строку таблицы.
 * @param {type} cssClass css-класс строки
 * @param {type} id id записи
 * @param {type} description описание записи
 * @param {type} done выполненное дело?
 * @returns {$} строка таблицы
 */
function tableRow(cssClass, id, description, done) {
    var row = "<tr class='" + cssClass + "'>\n";
    row += "\t<td class='id'>" + id + "</td>";
    row += "<td class='description'>" + description + "</td>";
    row += "<td><input class='done' type='checkbox' " + (done ? "checked" : "") + "></input></td>\n";
    row += "</tr>";
    row = $(row);
    return row;
}

/**
 * Выделить запись.
 * @param {type} e событие
 */
function selectRow(e) {
    if (e.ctrlKey && selectMode) {
        var tr = $(this).parent();
        if (tr.hasClass("selected")) {
            tr.removeClass("selected");
        } else {
            tr.addClass("selected");
        }
        var selectedCount = $("tr.selected").length;
        if (selectedCount > 0) {
            if (!editMode) {
                var button = $("<input type='button' "
                        + "id='cancel-selection' "
                        + "onclick='cancelSelection();' "
                        + "value='Cancel selection'/>");
                $("#todos").before(button);
                turnEditMode();
            }
        } else {
            $("#cancel-selection").remove();
            turnOffEditMode();
        }
    }
}

function cancelSelection() {
    $("#cancel-selection").remove();
    $("tr.selected").removeClass("selected");
    turnOffEditMode();
}

function convertTextInInputField(e) {
    if (!e.ctrlKey) {
        selectMode = false;
        var td = $(this);
        var text = td.text();
        if (text && !editMode) {
            turnEditMode();
            td.text("");
            firstText = text;
            $(td).append(textField(text));
            $(td).append(saveButton());
            $(td).append(cancelButton());
        }
    }
}

/**
 * Добавить новую запись в базу данных.
 */
function add() {
    var td = $(this).parent();
    var text = td.children(".new-todo-input")[0].value;
    $(this).off("click");
    $(this).parent().parent().remove();

    ajax(location.href + "add.json", {description: text}, load);
    addMode = false;
    selectMode = true;
}

/**
 * Отменить добавление новой записи в базу данных.
 */
function cancelAddition() {
    $(this).parent().parent().remove();
    addMode = false;
    selectMode = true;
}

/**
 * Сохранить значение записи в базе данных.
 */
function save() {
    var td = $(".save-button").parent();
    var text = td.children(".input-field")[0].value;
    $(".input").remove();
    td.text(text);
    if (text !== firstText) {
        var id = td.parent().children(".id").text();
        ajax(location.href + "update.json", {
            id: id,
            description: text
        });
    }
    turnOffEditMode();
}

/**
 * Отменить изменение записи.
 */
function cancel() {
    var td = $(".cancel-button").parent();
    $(".input").remove();
    td.text(firstText);
    turnOffEditMode();
}

/**
 * Изменить значение поля done в базе данных.
 */
function changeDoneField() {
    var checked = $(this)[0].checked;
    var td = $(this).parent();
    var id = td.parent().children(".id").text();
    ajax(location.href + "update.json", {id: id, done: checked});
}

/**
 * Включить режим редактирования.
 */
function turnEditMode() {
    if (!editMode) {
        editMode = true;
        $(".page-select").off("page");
        $("#categories").attr("disabled", true);
        $("#sort-by").attr("disabled", true);
        $("#sort-order").attr("disabled", true);
        $("#row-count").attr("disabled", true);
        $(".add-todo-button").off("click");
        $(".cancel-add-todo-button").off("click");
    }
}

/**
 * Выключить режим редактирования.
 */
function turnOffEditMode() {
    if (editMode) {
        selectMode = true;
        editMode = false;
        $(".page-select").on("page", function(event, num) {
            page = num;
            load();
        });
        $("#categories").attr("disabled", false);
        $("#sort-by").attr("disabled", false);
        $("#sort-order").attr("disabled", false);
        $("#row-count").attr("disabled", false);
        $(".add-todo-button").click(add);
        $(".cancel-add-todo-button").click(cancelAddition);
    }
}

function saveButton() {
    return $("<input class='input save-button' type='button' onclick='save();' value='Save'/>");
}

function cancelButton() {
    return $("<input class='input cancel-button' type='button' onclick='cancel();' value='Cancel'/>");
}

function deleteButton() {
    return $("<input class='delete-button' type='button' value='Delete'/>");
}

function textField(text) {
    return $("<textarea class='input input-field'>" + text + "</textarea>");
}

function ajax(url, data, success) {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: success
    });
}
