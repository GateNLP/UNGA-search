<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>UN GA Resolutions</title>
    <asset:stylesheet src="d3_clustergram.css"/>
</head>
<body>
<g:javascript>
var network_data = JSON.parse("${matrix}");
</g:javascript>

<div id="content" role="main">
    <section class="row colset-2-its">
        <div>
            <p>Sentence level co-occurrences</p>

            <!--    <h1>UN GA Resolutions</h1>-->

            <div id="wrapper">
                <div id='svg_div'></div>
            </div>
        </div>
    </section>


</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>


<script src="${resource(dir: 'js', file: 'd3_clustergram.js')}"></script>

<g:form name="sentences" controller="search" action="sentences">
    <input type="hidden" id="source" name="source"/>
    <input type="hidden" id="target" name="target"/>
    <input type="hidden" id="from" name="from"/>
    <input type="hidden" id="to" name="to"/>
</g:form>

</body>
</html>
