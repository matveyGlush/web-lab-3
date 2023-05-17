function history(checks) {
    const hist = document.createElement('div');
    checks.forEach(check => hist.append(historyUnit(check)));
    return hist;
}

function historyUnit(check) {
    const x = document.createElement('div');
    x.classList.add('x');
    x.innerHTML = "x: " + check.x;

    const y = document.createElement('div');
    y.classList.add('y');
    y.innerHTML = "y: " + check.y;

    const r = document.createElement('div');
    r.classList.add('r');
    r.innerHTML = "r: " + check.r;

    const result = document.createElement('div');
    result.classList.add('result');
    result.innerHTML = "result: ";
    if (check.areaContainsPoint === "true")
        result.innerHTML += "contains";
    else
        result.innerHTML += "doesn't contain";

    const requestDate = document.createElement('div');
    requestDate.classList.add('request-date');
    requestDate.innerHTML = "request date: " + check.requestDate;

    const calculationTime = document.createElement('div');
    calculationTime.classList.add('calculation-time');
    calculationTime.innerHTML = "calculation time: " + check.calculationTime;

    const ret = document.createElement('div');
    [x, y, r, result, requestDate, calculationTime].forEach(el => ret.append(el));
    return ret;
}

function pointField(checks, pointsRendered = 0, field = document.createElement('div')) {
    if (pointsRendered === checks.length)
        return field;
    const currentCheck = checks[pointsRendered];
    field.append(point(currentCheck, pointsRendered));
    return pointField(checks, pointsRendered + 1, field);
}

function point(check, pointsRendered) {
    const left = (check.x - AREA.inReal.leftX) * (AREA.inPx.width / AREA.inReal.width) - POINT_RADIUS_IN_PX;
    const top = (AREA.inReal.topY - check.y) * (AREA.inPx.height / AREA.inReal.height)
        - pointsRendered * 2 * POINT_RADIUS_IN_PX - POINT_RADIUS_IN_PX;
    const p = document.createElement('div');
    p.classList.add('point');
    p.style = pointStyle(top, left, check.areaContainsPoint === "true");
    p.title = pointTitle(check);
    return p;
}

function pointStyle(top, left, contains) {
    return "position: relative;" +
        " top: " + top + "px;" +
        " left: " + left + "px;" +
        " width: " + 2 * POINT_RADIUS_IN_PX + "px;" +
        " height: " + 2 * POINT_RADIUS_IN_PX + "px;" +
        " border-radius: " + POINT_RADIUS_IN_PX + "px;" +
        "background-color: " + (contains? "green" : "red") + ";";
}

function pointTitle(check) {
    return "Point: (" + check.x + ", " + check.y
        + ", contains?: " + check.areaContainsPoint
        + ", last recalculation(for current radius): " + check.RequestDate;

}

function areaWidthInPx() {
    return (2 * AREA.inReal.r / AREA.inReal.width) * AREA.inPx.width;
}

function areaHeightInPx() {
    return (2 * AREA.inReal.r / AREA.inReal.height) * AREA.inPx.height;
}

function calculateXY(xClickCoordinate, yClickCoordinate, areaTopLeftCornerXCoordinate, areaTopLeftCornerYCoordinate, areaWidthInPx, areaHeightInPx) {
    let centerCoordinateY = areaTopLeftCornerYCoordinate - (areaHeightInPx / 2);
    let centerCoordinateX = areaTopLeftCornerXCoordinate + (areaWidthInPx / 2);
    let centerAndClickWidthDifferencePx = xClickCoordinate - centerCoordinateX;
    let centerAndClickHeightDifferencePx = yClickCoordinate - centerCoordinateY;
    let yOut = (centerAndClickHeightDifferencePx / areaHeightInPx) * AREA.inReal.height;
    let xOut = (centerAndClickWidthDifferencePx / areaWidthInPx) * AREA.inReal.width;
    return {
        x: +(xOut).toPrecision(2),
        y: +(yOut).toPrecision(2)
    }

}

function getXYInputFromAreaClickAndSubmitForm(xClick, yClick) {
    let area = document.getElementById("axis");
    let xClickCoordinate = xClick;
    let yClickCoordinate = yClick;
    let areaTopLeftCornerXCoordinate = getOffset(area).left;
    let areaTopLeftCornerYCoordinate = -1 * getOffset(area).top;
    let areaWidthInPx = area.offsetWidth;
    let areaHeightInPx = area.offsetHeight;
    let x = calculateXY(xClickCoordinate, yClickCoordinate, areaTopLeftCornerXCoordinate, areaTopLeftCornerYCoordinate, areaWidthInPx, areaHeightInPx).x;
    let y = calculateXY(xClickCoordinate, yClickCoordinate, areaTopLeftCornerXCoordinate, areaTopLeftCornerYCoordinate, areaWidthInPx, areaHeightInPx).y;

    document.getElementById("main-form:x-input").value = x;
    document.getElementById("main-form:y-input").value = y;
    let sub = document.getElementById('main-form:main-submit');
    sub.click();

}

function areaClicked(e) {
    let xClick = e.clientX;
    let yClick = -1 * e.clientY;
    getXYInputFromAreaClickAndSubmitForm(xClick, yClick);
}

function getOffset(el) {
    const rect = el.getBoundingClientRect();
    return {
        left: rect.left + window.scrollX,
        top: rect.top + window.scrollY
    };
}

function reactToChangeRRadio(newR) {
    document.getElementById("r-form:r-input").value = newR;
    document.getElementById('r-form:r-submit').click();
}
function reactToXChanged(newX) {
    document.getElementById("main-form:x-input").value = newX;
}