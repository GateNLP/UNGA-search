<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>UN GA Resolutions</title>
</head>
<body>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Food Security Media Analysis Knowledgebase</h1>

        <g:form controller="search" action="results">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="keywords1">I Am Searching For...</label>
                    <input type="text" class="form-control" name="keywords1">
                </div>
                <div class="form-group col-md-6">
                    <label for="keywords2">Related To...</label>
                    <input type="text" class="form-control" name="keywords2">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="from">Between...</label>
                    <g:datePicker name="from" precision="day" default="${new Date(grailsApplication.config.unga.startYear-1900,grailsApplication.config.unga.startMonth-1,grailsApplication.config.unga.startDay)}"/>
                </div>
                <div class="form-group col-md-6">
                    <label for="to">and...</label>
                    <g:datePicker name="to" precision="day"/>
                </div>
            </div>

            <div class="form-row pull-right">
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </div>
        </g:form>

    </section>
</div>

</body>
</html>
