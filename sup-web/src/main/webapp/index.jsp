<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/MyGarage.css"/>
    <title>MyGarage une mise en oeuvre de ce que j'apprend avec OCR</title>
</head>
<body>

<header class="introduction">Application de mise en oeuvre de ma formation OCR dans le contexte badge Epita
</header>
<div class="application">
    <nav>
        <iframe class="menu" title="Menu" name="menu" src="${pageContext.request.contextPath}/doMenuGarage"></iframe>
    </nav>
    <section class="content">
        <iframe class="content" title="Contenu" name="Contenu" src="${pageContext.request.contextPath}/doAbout"></iframe>
    </section>
</div>

</body>
</html>