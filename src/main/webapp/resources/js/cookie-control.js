if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (str) {
        return this.slice(0, str.length) == str;
    };
}


// получение всех куков в объект
function getCookies() {
    var res = {}, coo,
        cArr = document.cookie.split(/;\s?/);
    for (var i = 0; i < cArr.length; i++) {
        coo = cArr[i].split('=');
        res[coo[0]] = decodeURIComponent(coo[1]);
    }
    return res;
}

// Устанавливаем куки
function setCookie(name, value, options) {
    options = options || {};

    var expires = options.expires;

    if (typeof expires == "number" && expires) {
        var d = new Date();
        d.setTime(d.getTime() + expires * 1000);
        expires = options.expires = d;
    }
    if (expires && expires.toUTCString) {
        options.expires = expires.toUTCString();
    }

    value = encodeURIComponent(value);

    var updatedCookie = name + "=" + value;

    for (var propName in options) {
        updatedCookie += "; " + propName;
        var propValue = options[propName];
        if (propValue !== true) {
            updatedCookie += "=" + propValue;
        }
    }

    updatedCookie += "; path=/";

    document.cookie = updatedCookie;
}