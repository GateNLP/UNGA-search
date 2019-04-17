<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>UN GA Resolutions</title>
</head>
<body>


<div id="content" role="main">

    <section class="row colset-2-its">
        <div>
            <p>Sentence level co-occurences of <i>${params.source}</i> and <i>${params.target}</i> between ${params.from} and ${params.to}</p>
            <g:each var="document" in="${results}" >
                <div>
                    <h1><a href="${document.getURL()}">${document.getTitle()}</a></h1>

                    <g:each var="sentence" in="${document.getSentences()}" >
                        <p>${sentence}</p>
                    </g:each>
                </div>

            </g:each>
        </div>
    </section>
</div>

</body>
</html>