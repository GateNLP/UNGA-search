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

        <g:form controller="search" action="results" id="form">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="keywords1">List of keywords or annotation types:</label>
                    <input type="text" class="form-control" id="keywords1" name="keywords1"
                           required
                           placeholder="cuba economic commercial $Person"/>
                </div>

                <div class="form-group col-md-6">
                    <label for="keywords2">Second list to compare with (optional):</label>
                    <input type="text" class="form-control" id="keywords2" name="keywords2"
                           placeholder='"United States" embargo $Organization'/>
                </div>
            </div>

            <div class="form-row">
                <span>Search for co-occurrences within sentences</span>

                <g:select id="active" name="searchArea"
                          from="${['in preamble paragraphs only', 'in operative paragraphs only', 'anywhere in the document']}"
                          keys="${['preamble', 'operative', 'anywhere']}"
                          value="${'anywhere'}" />
            </div>

            <div class="form-row pull-right">
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary"
                            id="submit">Search</button>
                </div>
            </div>
        </g:form>

    </section>

    <p>In each text box above, you can enter a space-separated list of keywords or annotation types
    (such as $Person, $Organization, $Location, $Date, $UNBIS) to search for.
    Keyphrases which contains spaces should be in
    "double quotes".  The second list is optional.</p>
    <p>If one list is given, this tool will search for occurrences of each keyword/phrase and
    annotation type.</p>
    <p>If two lists are given, it will search for co-occurrences within sentences of each possible pair,
    one from the first list and one from the second.</p>
    <p>By default, the search covers whole documents, but you can restrict it to either preamble paragraphs
    or operative paragraphs only.</p>
</div>

</body>
</html>
