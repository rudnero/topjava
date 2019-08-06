// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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

function activate(checkbox, id){
    const urlActivate = context.ajaxUrl + "activate";
    const enabled = checkbox.is(":checked");
    $.ajax({
            type: "POST",
            url: urlActivate,
            data: {active : enabled, id : id},
    }).done(function () {
        checkbox.find("tr").attr("data-userEnabled", enabled);
        successNoty("Saved");
    }).fail(function () {
        checkbox.prop("checked", !enabled);
    });
}