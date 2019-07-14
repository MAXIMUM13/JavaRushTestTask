'use strict';

let page = 1;
let count;
let total;

let editMode = false;
let addMode = false;
let selectMode = true;
let firstText;

$(function () {
    ajax(location.href + 'count/' + category(), null, function (data) {
        count = parseInt(data);
        let rows = rowCount();
        total = Math.ceil(count / rows);
        $('.page-select').bootpag({
            total: total,
            maxVisible: 10
        }).on('page', function (e, num) {
            page = num;
            load();
        });
        load();
        $('#categories').change(load);
        $('#sort-by').change(load);
        $('#sort-order').change(load);
        $('#row-count').change(load);
        $('#add-todo').click(addTodo);
        $('#delete-todos').click(deleteTodos);
    });
});

function category() {
    console.log($('#categories').val(), $('#categories').text());
    return $('#categories')[0].value;
}

function sortBy() {
    console.log($('#sort-by').val(), $('#sort-by').text());
    return $('#sort-by')[0].value;
}

function order() {
    console.log($('#sort-order').val(), $('#sort-order').text());
    return $('#sort-order')[0].value;
}

function rowCount() {
    console.log($('#row-count').val(), $('#row-count').text());
    return $('#row-count')[0].value;
}

function load() {
    ajax(location.href + 'count/' + category(), null, function (data) {
        count = parseInt(data);
        let rows = rowCount();
        total = Math.ceil(count / rows);
        if (page > total) {
            page = total;
        }

        $('.page-select').bootpag({
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
        category = 'all';
    }
    if (!sortBy) {
        sortBy = 'id';
    }
    if (!order) {
        order = 'asc';
    }
    if (!count) {
        count = rowCount();
    }

    let url = location.href + 'todos.json';
    let table = $('#todos');
    ajax(url, {
        page: num,
        category: category,
        sort_by: sortBy,
        order: order,
        count: count
    }, function (data) {
        console.log(13, data);
        $('.table-row').remove();
        for (let i in data) {
            let id = data[i].id;
            let description = data[i].description;
            let done = data[i].done;
            let row = tableRow('table-row', id, description, done);
            row.appendTo(table);
        }

        let descriptionField = $('.description');
        descriptionField.click(selectRow);
        descriptionField.dblclick(convertTextInInputField);
        $('.done').click(changeDoneField);
    });
}

function addTodo() {
    if (!editMode && !addMode) {
        addMode = true;
        let tr = $('<tr class="tr">'
            + '<td>#</td>'
            + '<td>'
            + '<textarea class="new-todo-input"></textarea>'
            + '<input class="add-todo-button" type="button" value="Save"/>'
            + '<input class="cancel-add-todo-button" type="button" value="Cancel"/>'
            + '</td>'
            + '<td></td>'
            + '</tr>');
        let header = $('#header');
        header.after(tr);
        $('.add-todo-button').click(add);
        $('.cancel-add-todo-button').click(cancelAddition);
    }
}

function deleteTodos() {
    let ids = [];
    $('.table-row.selected').each(function (i, el) {
        ids[ids.length] = $(el).children('.id').text();
    });
    ajax(location.href + 'delete.json', {
        ids: ids.toString()
    }, function () {
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
    let row = '<tr class="' + cssClass + '">';
    row += '<td class="id">' + id + '</td>';
    row += '<td class="description">' + description + '</td>';
    row += '<td><input class="done" type="checkbox" ' + (done ? 'checked' : '') + '/></td>';
    row += '</tr>';
    row = $(row);
    return row;
}

/**
 * Выделить запись.
 * @param {type} e событие
 */
function selectRow(e) {
    if (e.ctrlKey && selectMode) {
        let tr = $(this).parent();
        if (tr.hasClass('selected')) {
            tr.removeClass('selected');
        } else {
            tr.addClass('selected');
        }
        let selectedCount = $('tr.selected').length;
        if (selectedCount > 0) {
            if (!editMode) {
                let button = $('<input>');
                button.attr('id', 'cancel-selection');
                button.attr('type', 'button');
                button.click(cancelSelection);
                $('#todos').before(button);
                turnEditMode();
            }
        } else {
            $('#cancel-selection').remove();
            turnOffEditMode();
        }
    }
}

function cancelSelection() {
    $('#cancel-selection').remove();
    $('tr.selected').removeClass('selected');
    turnOffEditMode();
}

function convertTextInInputField(e) {
    if (!e.ctrlKey) {
        selectMode = false;
        let td = $(this);
        let text = td.text();
        if (text && !editMode) {
            turnEditMode();
            td.text('');
            firstText = text;
            td.append(textField(text));
            td.append(saveButton());
            td.append(cancelButton());
        }
    }
}

/**
 * Добавить новую запись в базу данных.
 */
function add() {
    let td = $(this).parent();
    let text = td.children('.new-todo-input').val();
    $(this).off('click');
    $(this).parent().parent().remove();

    ajax(location.href + 'add.json', {
        description: text
    }, load);
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
    let td = $('.save-button').parent();
    let text = td.children('.input-field').val();
    $('.input').remove();
    td.text(text);
    if (text !== firstText) {
        let id = td.parent().children('.id').text();
        ajax(location.href + 'update.json', {
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
    let td = $('.cancel-button').parent();
    $('.input').remove();
    td.text(firstText);
    turnOffEditMode();
}

/**
 * Изменить значение поля done в базе данных.
 */
function changeDoneField() {
    let checked = $(this)[0].checked;
    let td = $(this).parent();
    let id = td.parent().children('.id').text();
    ajax(location.href + 'update.json', {
        id: id,
        done: checked
    });
}

/**
 * Включить режим редактирования.
 */
function turnEditMode() {
    if (!editMode) {
        editMode = true;
        $('.page-select').off('page');
        $('#categories').attr('disabled', true);
        $('#sort-by').attr('disabled', true);
        $('#sort-order').attr('disabled', true);
        $('#row-count').attr('disabled', true);
        $('.add-todo-button').off('click');
        $('.cancel-add-todo-button').off('click');
    }
}

/**
 * Выключить режим редактирования.
 */
function turnOffEditMode() {
    if (editMode) {
        selectMode = true;
        editMode = false;
        $('.page-select').on('page', function (event, num) {
            page = num;
            load();
        });
        $('#categories').attr('disabled', false);
        $('#sort-by').attr('disabled', false);
        $('#sort-order').attr('disabled', false);
        $('#row-count').attr('disabled', false);
        $('.add-todo-button').click(add);
        $('.cancel-add-todo-button').click(cancelAddition);
    }
}

function saveButton() {
    let button = $('<input>');
    button.addClass('input save-button');
    button.attr('type', 'button');
    button.val('Save');
    button.click(save);
    return button;
}

function cancelButton() {
    let button = $('<input>');
    button.addClass('input cancel-button');
    button.attr('type', 'button');
    button.val('Cancel');
    button.click(cancel);
    return button;
}

function deleteButton() {
    let button = $('<input>');
    button.addClass('delete-button');
    button.attr('type', 'button');
    button.val('Cancel');
    //button.click(delete);
    return button;
}

function textField(text) {
    let textField = $('<textarea>');
    textField.addClass('input input-field');
    textField.text(text);
    return textField;
}

function ajax(url, data, success) {
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        success: success
    });
}
