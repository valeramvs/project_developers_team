/**
 * Function changeTabs() displays selected UI for various user groups (clients, managers, developers),
 * also changes style of the selected tab.
 */
window.onload = function() {
    document.querySelector('.tabs_header').addEventListener('click', changeTabs);
    function changeTabs(event) {
        if (event.target.className == 'tab') {
            let dataTab = event.target.getAttribute('data-tab');
            let tabs = document.querySelector('.tabs_header').children;
            let forms = document.querySelector('.tabs_body').children;
            for (let i = 0; i < forms.length; i++) {
                if (dataTab == i) {
                    tabs[i].style.background = '#1995ad';
                    tabs[i].style.color = 'white';
                    forms[i].style.display = 'block';
                } else {
                    tabs[i].style.background = '#a1d6e2';
                    tabs[i].style.color = 'black';
                    forms[i].style.display = 'none';
                }
            }
        }
    }
}

let validation = true;

let resultSpec = document.getElementById('result_spec');
let resultBill = document.getElementById('result_bill');
/**
 * Function introduceSpec() reads data (client's name, email, specification and tasks) and
 * sends POST request to create a new client or find the existing client and introduce
 * the specification's data to the manager.
 */
document.getElementById('introduce_spec').addEventListener('click', introduceSpec);
function introduceSpec(event) {
    event.preventDefault();
    resultBill.style.display = 'none';
    resultSpec.style.display = 'block';
    let sendSpec = document.getElementById('send_spec');
    sendSpec.onclick = function(event) {
        event.preventDefault();
        document.getElementById('email_error').innerHTML = '';
        document.getElementById('spec_info').innerHTML = '';
        let email = document.getElementById('email_client').value;
        let name = document.getElementById('name_client').value;
        let spec = document.getElementById('name_spec').value;
        let rows = document.getElementById('tasks_table').rows;
        let tasks = [];
        for (let i = 1; i < rows.length; i++) {
            let task = [];
            for (let j = 1; j < 5; j++) {
                if (rows[i].cells[j].firstChild.value != '') {
                    task[j-1] = rows[i].cells[j].firstChild.value;
                } else {
                    task[j-1] = '0';
                }
            }
            tasks[i-1] = task;
        }
        emailValid(email);
        if (validation) {
            document.getElementById('email_error').innerHTML = '';
        } else {
            document.getElementById('email_error').innerHTML = 'Invalid email!';
            return false;
        }
        console.log('Try send spec with email: ' + email
            + ', name: ' + name + ', spec: ' + spec + ', tasks: ' + tasks);
        let xhr = new XMLHttpRequest();
        if (!xhr) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }
        let client = JSON.stringify({
            email: email,
            name: name,
            spec: spec,
            tasks: tasks
        });
        xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.client.IntroduceSpecServlet');
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(client);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                let response = xhr.response;
                document.getElementById('spec_info').innerHTML = response;
            }
        }
    }
}

/**
 * Function addTask() adds a new row to the table for tasks.
 */
let addTask = document.getElementById('addTask');
addTask.onclick = function(event) {
    event.preventDefault();
    let table = document.getElementById('tasks_table').getElementsByTagName('TBODY')[0];
    let row = document.createElement('TR');
    table.appendChild(row);
    for (let i = 0; i < 5; i++) {
        let td = document.createElement('TD');
        row.appendChild(td);
        let input = document.createElement('INPUT');
        td.appendChild(input)
    }
}

/**
 * Function showBill() sends POST request to get information about client's specification including the bill.
 */
document.getElementById('show_bill').addEventListener('click', showBill);
function showBill(event) {
    event.preventDefault();
    resultSpec.style.display = 'none';
    resultBill.style.display = 'block';
    let clientInfo = document.getElementById('client_info');
    clientInfo.style.display = 'none';
    let checkBill = document.getElementById('check_bill');
    checkBill.onclick = function(event) {
        event.preventDefault();
        document.getElementById('email_error_bill').innerHTML = '';
        document.getElementById('bill_info').innerHTML = '';
        let email = document.getElementById('email_client_bill').value;
        let name = document.getElementById('name_client_bill').value;
        emailValid(email);
        if (validation) {
            document.getElementById('email_error_bill').innerHTML = '';
        } else {
            document.getElementById('email_error_bill').innerHTML = 'Invalid email!';
            return false;
        }
        console.log('Try check bill with email: ' + email
            + ', name: ' + name);
        let xhr = new XMLHttpRequest();
        if (!xhr) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }
        let client = JSON.stringify({
            email: email,
            name: name
        });
        xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.client.CheckBillServlet');
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(client);
        xhr.responseType = 'json';
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                let response = xhr.response;
                if (response[0] == 'Client with these email and name does not exist in database!') {
                    clientInfo.style.display = 'none';
                    document.getElementById('bill_info').innerHTML = response;
                } else {
                    clientInfo.style.display = 'block';
                    document.getElementById('bill_info').innerHTML = '';
                    let clientTable = document.getElementById('client_table');
                    let row = clientTable.insertRow(clientTable.rows.length);
                    let cellId = row.insertCell(0);
                    let cellName = row.insertCell(1);
                    let cellEmail = row.insertCell(2);
                    let id = document.createTextNode(response[0].id);
                    let name = document.createTextNode(response[0].name);
                    let email = document.createTextNode(response[0].email);
                    cellId.append(id);
                    cellName.append(name);
                    cellEmail.append(email);
                    let specsTable = document.getElementById('specs_table');
                    row = specsTable.insertRow(specsTable.rows.length);
                    let specs = response[1];
                    for (let i = 0; i < specs.length; i++) {
                        row = specsTable.insertRow(specsTable.rows.length);
                        let cellIdSpec = row.insertCell(0);
                        let cellBill = row.insertCell(1);
                        let cellManagerName = row.insertCell(2);
                        let cellManagerEmail = row.insertCell(3);
                        let cellDelete = row.insertCell(4);
                        let spec = specs[i];
                        let idSpec = document.createTextNode(spec.idSpec);
                        let bill = document.createTextNode(spec.bill);
                        let managerName = document.createTextNode(spec.managerName);
                        let managerEmail = document.createTextNode(spec.managerEmail);

                        /* Button 'Delete' adds only to specifications which haven't had the manager yet */
                        if (spec.bill == '–') {
                            let button = document.createElement('INPUT');
                            button.type = 'submit';
                            button.classList.add('delete_spec');
                            button.value = 'Delete';
                            button.addEventListener('click', deleteSpecification);
                            cellDelete.appendChild(button);
                        }
                        cellIdSpec.append(idSpec);
                        cellBill.append(bill);
                        cellManagerName.append(managerName);
                        cellManagerEmail.append(managerEmail);

                    }
                }
            }
        }
    }
}

/**
 * Function deleteSpecification() removes the specification and sends POST request with specification's id
 * for delete data about this specification from database.
 */
function deleteSpecification(event) {
    event.preventDefault();
    let tr = this.parentNode.parentNode;
    let id = tr.firstChild.textContent;
    tr.parentNode.removeChild(tr);
    let xhr = new XMLHttpRequest();
    if (!xhr) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    let spec = JSON.stringify({
        id: id
    });
    xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.client.DeleteSpecServlet');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(spec);
}

/**
 * Function showSpecs() reads data (manager's name and email) and sends POST request
 * to create a new manager or find the existing manager and show specification which haven't
 * had the manager yet for creating the project by selected specification.
 */
let notReviewedSpecs = document.getElementById('not_reviewed_specs');
let reviewedSpecs = document.getElementById('reviewed_specs');
document.getElementById('show_specs').addEventListener('click', showSpecs);
function showSpecs(event) {
    event.preventDefault();
    reviewedSpecs.style.display = 'none';
    notReviewedSpecs.style.display = 'block';
    let freeSpecs = document.getElementById('free_specs');
    freeSpecs.style.display = 'none';
    let showFreeSpecs = document.getElementById('show_free_specs');
    showFreeSpecs.onclick = function(event) {
        event.preventDefault();
        document.getElementById('email_manager_error').innerHTML = '';
        document.getElementById('free_spec_error').innerHTML = '';
        document.getElementById('free_spec_info').innerHTML = '';
        let email = document.getElementById('email_manager').value;
        let name = document.getElementById('name_manager').value;
        emailValid(email);
        if (validation) {
            document.getElementById('email_manager_error').innerHTML = '';
        } else {
            document.getElementById('email_manager_error').innerHTML = 'Invalid email!';
            return false;
        }
        console.log('Try show free specs with email: ' + email
            + ', name: ' + name);
        let xhr = new XMLHttpRequest();
        if (!xhr) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }
        let manager = JSON.stringify({
            email: email,
            name: name
        });
        xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.manager.ShowSpecsServlet');
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(manager);
        xhr.responseType = 'json';
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                let response = xhr.response;
                if (response == "There are not free specifications!") {
                    freeSpecs.style.display = 'none';
                    document.getElementById('free_spec_info').innerHTML = response;
                } else {
                    freeSpecs.style.display = 'block';
                    document.getElementById('free_spec_info').innerHTML = '';
                    let freeSpecsTable = document.getElementById('free_specs_table');
                    for (let i = 0; i < response.length; i++) {
                        let row = freeSpecsTable.insertRow(freeSpecsTable.rows.length);
                        let cellRadio = row.insertCell(0);
                        let cellId = row.insertCell(1);
                        let cellClientName = row.insertCell(2);
                        let cellClientEmail = row.insertCell(3);
                        let cellTasks = row.insertCell(4);
                        let cellDevs = row.insertCell(5);
                        let button = document.createElement('INPUT');
                        button.type = 'radio';
                        button.name = 'chooseSpec';
                        let id = document.createTextNode(response[i].id);
                        let clientName = document.createTextNode(response[i].clientName);
                        let clientEmail = document.createTextNode(response[i].clientEmail);
                        let tasks = document.createTextNode(response[i].tasks);
                        let devs = document.createTextNode(response[i].devs);
                        cellRadio.appendChild(button);
                        cellId.append(id);
                        cellClientName.append(clientName);
                        cellClientEmail.append(clientEmail);
                        cellTasks.append(tasks);
                        cellDevs.append(devs);
                    }
                }
            }
        }
    }
}

/**
 * Function createProject() sends POST request for creating the project by the selected specification.
 */
let createProject = document.getElementById('create_project');
createProject.onclick = function(event) {
    event.preventDefault();
    document.getElementById('free_spec_error').innerHTML = '';
    let email = document.getElementById('email_manager').value;
    let name = document.getElementById('name_manager').value;
    let rad = document.getElementsByName('chooseSpec');
    let idSpec = '0';
    for (let i = 0; i < rad.length; i++) {
        if (rad[i].checked) {
            let tr = rad[i].parentNode.parentNode;
            idSpec = tr.cells[1].textContent;
        }
    }
    if (idSpec == '0') {
        document.getElementById('free_spec_error').innerHTML = 'Choose the specification to create the project!'
        return;
    }
    console.log('Try create project with email: ' + email
        + ', name: ' + name + ', idSpec: ' + idSpec);
    let xhr = new XMLHttpRequest();
    if (!xhr) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    let spec = JSON.stringify({
        idSpec: idSpec,
        email: email,
        name: name
    });
    xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.manager.CreateProjectServlet');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(spec);
    xhr.responseType = 'json';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            let response = xhr.response;
            document.getElementById('free_spec_info').innerHTML = response;
        }
    }
}

/**
 * Function checkSpecs() sends POST request to check their projects and make the tables
 * with personal information about the manager, information about their project's client
 * and project's tasks.
 */
document.getElementById('check_specs').addEventListener('click', checkSpecs);
function checkSpecs(event) {
    event.preventDefault();
    reviewedSpecs.style.display = 'block';
    notReviewedSpecs.style.display = 'none';
    let managerInfo = document.getElementById('manager_info');
    managerInfo.style.display = 'none';
    let checkProject = document.getElementById('check_project');
    checkProject.onclick = function(event) {
        event.preventDefault();
        document.getElementById('email_error_proj').innerHTML = '';
        document.getElementById('project_info').innerHTML = '';
        let email = document.getElementById('email_manager_proj').value;
        let name = document.getElementById('name_manager_proj').value;
        emailValid(email);
        if (validation) {
            document.getElementById('email_error_proj').innerHTML = '';
        } else {
            document.getElementById('email_error_proj').innerHTML = 'Invalid email!';
            return false;
        }
        console.log('Try check project with email: ' + email
            + ', name: ' + name);
        let xhr = new XMLHttpRequest();
        if (!xhr) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }
        let manager = JSON.stringify({
            email: email,
            name: name
        });
        xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.manager.CheckSpecsServlet');
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(manager);
        xhr.responseType = 'json';
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                let response = xhr.response;
                if (response == 'Manager with these email and name does not exist in database!') {
                    managerInfo.style.display = 'none';
                    document.getElementById('project_info').innerHTML = response;
                } else {
                    managerInfo.style.display = 'block';
                    document.getElementById('project_info').innerHTML = '';
                    let managerTable = document.getElementById('manager_table');
                    let row = managerTable.insertRow(managerTable.rows.length);
                    let cellId = row.insertCell(0);
                    let cellName = row.insertCell(1);
                    let cellEmail = row.insertCell(2);
                    let id = document.createTextNode(response[0].id);
                    let name = document.createTextNode(response[0].name);
                    let email = document.createTextNode(response[0].email);
                    cellId.append(id);
                    cellName.append(name);
                    cellEmail.append(email);
                    let projectTable = document.getElementById('project_table');
                    let projects = response[1];
                    for (let i = 0; i < projects.length; i++) {
                        row = projectTable.insertRow(projectTable.rows.length);
                        let cellIdSpec = row.insertCell(0);
                        let cellBill = row.insertCell(1);
                        let cellClientName = row.insertCell(2);
                        let cellClientEmail = row.insertCell(3);
                        let project = projects[i];
                        let idSpec = document.createTextNode(project.idSpec);
                        let bill = document.createTextNode(project.bill);
                        let clientName = document.createTextNode(project.clientName);
                        let clientEmail = document.createTextNode(project.clientEmail);
                        cellIdSpec.append(idSpec);
                        cellBill.append(bill);
                        cellClientName.append(clientName);
                        cellClientEmail.append(clientEmail);

                    }
                    let taskTable = document.getElementById('project_tasks_table');
                    let tasks = response[2];
                    for (let i = 0; i < tasks.length; i++) {
                        row = taskTable.insertRow(taskTable.rows.length);
                        let cellIdTask = row.insertCell(0);
                        let cellTeamLeaders = row.insertCell(1);
                        let cellSeniors = row.insertCell(2);
                        let cellMiddles = row.insertCell(3);
                        let cellJuniors = row.insertCell(4);
                        let cellIdSpec = row.insertCell(5);
                        let task = tasks[i];
                        let idTask = document.createTextNode(task.idTask);
                        let teamLeader = document.createTextNode(task.teamLeaders);
                        let senior = document.createTextNode(task.seniors);
                        let middle = document.createTextNode(task.middles);
                        let junior = document.createTextNode(task.juniors);
                        let idSpec = document.createTextNode(task.idSpec);
                        cellIdTask.append(idTask);
                        cellTeamLeaders.append(teamLeader);
                        cellSeniors.append(senior);
                        cellMiddles.append(middle);
                        cellJuniors.append(junior);
                        cellIdSpec.append(idSpec);
                    }
                }
            }
        }
    }
}

/**
 * Function setWorkTime() sends GET request to get the list of developers which were appointed
 * to the project and need to set their work time on this project.
 */
let workTime = document.getElementById('work_time');
let allDevs = document.getElementById('all_devs');
document.getElementById('set_work_time').addEventListener('click', setWorkTime);
function setWorkTime(event) {
    event.preventDefault();
    allDevs.style.display = 'none';
    workTime.style.display = 'block';
    let workTimeForDevs = document.getElementById('work_time_for_devs');
    console.log('Try show developers without work time');
    let xhr = new XMLHttpRequest();
    if (!xhr) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    xhr.open('GET', 'by.epam.outercourse.project.devteam.servlet.developer.ShowDevsWithoutTimeServlet');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send();
    xhr.responseType = 'json';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            let response = xhr.response;
            if (response[0] == "There are not developers to set work time!") {
                workTimeForDevs.style.display = 'none';
                document.getElementById('work_time_info').innerHTML = response[0];
            } else {
                workTimeForDevs.style.display = 'block';
                document.getElementById('work_time_info').innerHTML = '';
                let workTimeTable = document.getElementById('work_time_table');
                for (let i = 0; i < response.length; i++) {
                    let row = workTimeTable.insertRow(workTimeTable.rows.length);
                    let cellId = row.insertCell(0);
                    let cellQualification = row.insertCell(1);
                    let cellWorkTime = row.insertCell(2);
                    let cellIdSpec = row.insertCell(3);
                    let cellIdTask = row.insertCell(4);
                    let id = document.createTextNode(response[i].id);
                    let qualification = document.createTextNode(response[i].qualification);
                    let input = document.createElement('INPUT');
                    let idSpec = document.createTextNode(response[i].idSpec);
                    let idTask = document.createTextNode(response[i].idTask);
                    cellId.append(id);
                    cellQualification.append(qualification);
                    cellWorkTime.append(input);
                    cellIdSpec.append(idSpec);
                    cellIdTask.append(idTask);
                }
            }
        }
    }
    /**
     * Function confirmWorkTime() sends POST request with the list of developers' id and
     * the list of developers' work time on the project.
     */
    let confirmWorkTime = document.getElementById('confirm_work_time');
    confirmWorkTime.onclick = function(event) {
        event.preventDefault();
        let rows = document.getElementById('work_time_table').rows;
        let ids = [];
        let times = [];
        for (let i = 1; i < rows.length; i++) {
            ids[i-1] = rows[i].cells[0].textContent;
            if (rows[i].cells[2].firstChild.value != '') {
                times[i-1] = rows[i].cells[2].firstChild.value;
            } else {
                times[i-1] = '0';
            }
        }
        console.log('Try confirm the changes');
        let xhr = new XMLHttpRequest();
        if (!xhr) {
            console.log('Unable to create XMLHTTP instance');
            return false;
        }
        let time = JSON.stringify({
            ids: ids,
            times: times
        });
        xhr.open('POST', 'by.epam.outercourse.project.devteam.servlet.developer.ConfirmWorkTimeServlet');
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(time);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                let response = xhr.response;
                console.log(response);
                if (response == "Data was successfully added to the database!") {
                    document.getElementById('work_time_info').innerHTML = response;
                    document.getElementById('work_time_error').innerHTML = '';
                } else {
                    document.getElementById('work_time_error').innerHTML = 'Something went wrong!';
                    document.getElementById('work_time_info').innerHTML = '';
                }
            }
        }
    }
}

/**
 * Function showDevs() send GET request to show the list of all developers from database.
 */
document.getElementById('show_all_devs').addEventListener('click', showDevs);
function showDevs(event) {
    event.preventDefault();
    allDevs.style.display = 'block';
    workTime.style.display = 'none';
    console.log('Try show all developers');
    let xhr = new XMLHttpRequest();
    if (!xhr) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    xhr.open('GET', 'by.epam.outercourse.project.devteam.servlet.developer.ShowAllDevsServlet');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send();
    xhr.responseType = 'json';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            let response = xhr.response;
            let devsTable = document.getElementById('devs_table');
            for (let i = 0; i < response.length; i++) {
                let row = devsTable.insertRow(devsTable.rows.length);
                let cellId = row.insertCell(0);
                let cellQualification = row.insertCell(1);
                let cellWorkTime = row.insertCell(2);
                let cellIdSpec = row.insertCell(3);
                let cellIdTask = row.insertCell(4);
                let id = document.createTextNode(response[i].id);
                let qualification = document.createTextNode(response[i].qualification);
                let workTime = document.createTextNode(response[i].workTime);
                let idSpec = document.createTextNode(response[i].idSpec);
                let idTask = document.createTextNode(response[i].idTask);
                cellId.append(id);
                cellQualification.append(qualification);
                cellWorkTime.append(workTime);
                cellIdSpec.append(idSpec);
                cellIdTask.append(idTask);
            }
        }
    }
}

const EMAIL_REG_EXP = /[0-9a-z_-]+@[0-9a-z_-]+\.[a-z]{2,6}$/i;

/**
 * Function emailValid(email) checks the value of email according to the regular expression EMAIL_REF_EXP.
 */
function emailValid(email) {
    if (!email.match(EMAIL_REG_EXP)) {
        alert('Invalid email. EMAIL should only consist of letters (a-z, A-Z), numbers (0-9),'
            + ' signs ("-","_"), has @ and existing domain');
        validation = false;
    } else {
        validation = true;
    }
}