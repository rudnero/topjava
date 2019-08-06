function filterTable() {
    let form = $("#filterForm");
    let urlFilter = context.ajaxUrl + "filter/";
    let startDate = form.find("input[name=startDate]").val();
    let startTime = form.find("input[name=startTime]").val();
    let endDate = form.find("input[name=endDate]").val();
    let endTime = form.find("input[name=endTime]").val();
    urlFilter += "?"+"startDate="+startDate+"&startTime="+startTime+"&endDate="+endDate+"&endTime="+endTime;

    $.ajax({
        type: "GET",
        url: urlFilter,
        data: form.serialize()
    }).done(function (data) {
        updateTableWithData(data);
        form.attr("statusFilter", "true")
        successNoty("Filtered");
    })
}

function clearFilteredTable(){
    let form = $("#filterForm");
    form.find(":input").val("");
    form.attr("statusFilter", "false");
    updateTable();
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});