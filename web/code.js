/* jshint esversion: 6 */

document.getElementById('searchbutton').onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
        .then((response) => response.json())
        .then((data) => {
            document.getElementById("responsesize").innerHTML = data.length == 0 ? "<p>No web page contains the query word.</p>" :
                "<p>" + data.length + " websites retrieved</p>";
            let results = data.map((page) =>
                `<li><a href="${page.url}">${page.title}</a></li>`)
                .join("\n");
            document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
        });
};