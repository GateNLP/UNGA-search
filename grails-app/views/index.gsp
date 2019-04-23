<!doctype html>
<html xmlns:search="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="main"/>
    <title>UN GA Resolutions</title>
</head>

<body>

<div id="content" role="main">
<section class="row colset-2-its">
<h1>UN GA Resolutions</h1>

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

    <div class="form-row"
    <span>Search within areas of documents:</span>
    <g:radioGroup name="searchArea"
                  labels="['preamble', 'operative', 'anywhere']"
                  values="['preamble', 'operative', 'anywhere']">
        <p>${it.radio} ${it.label}</p>
    </g:radioGroup>
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
