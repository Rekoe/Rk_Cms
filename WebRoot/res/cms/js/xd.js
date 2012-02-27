var xd = function() {
    var T, p, s, q;
    function va(a, b) {
        var c = document.createElement("script");
        c.type = "text/javascript";
        c.async = !0;
        var d = !1;
        c.onreadystatechange = function() {
            if (("loaded" == this.readyState || "complete" == this.readyState) && !d) d = !0,
            b();
        };
        c.onload = b;
        c.src = a;
        var h = document.getElementsByTagName("script")[0];
        h.parentNode.insertBefore(c, h);
    }
    function U() {
        b = jQuery; (function(a) {
            a.fn.enter = function(b) {
                a.isFunction(b) && this.keypress(function(a) {
                    13 == a.keyCode && b(a);
                });
                return this;
            };
        })(jQuery);
    }
    function j(a, b) {
        return {
            valid: a,
            msg: b
        };
    }
    function wa(a) {
        var b = [],
        b = a.split("");
        if (null == {
            11 : "\u5317\u4eac",
            12 : "\u5929\u6d25",
            13 : "\u6cb3\u5317",
            14 : "\u5c71\u897f",
            15 : "\u5185\u8499\u53e4",
            21 : "\u8fbd\u5b81",
            22 : "\u5409\u6797",
            23 : "\u9ed1\u9f99\u6c5f",
            31 : "\u4e0a\u6d77",
            32 : "\u6c5f\u82cf",
            33 : "\u6d59\u6c5f",
            34 : "\u5b89\u5fbd",
            35 : "\u798f\u5efa",
            36 : "\u6c5f\u897f",
            37 : "\u5c71\u4e1c",
            41 : "\u6cb3\u5357",
            42 : "\u6e56\u5317",
            43 : "\u6e56\u5357",
            44 : "\u5e7f\u4e1c",
            45 : "\u5e7f\u897f",
            46 : "\u6d77\u5357",
            50 : "\u91cd\u5e86",
            51 : "\u56db\u5ddd",
            52 : "\u8d35\u5dde",
            53 : "\u4e91\u5357",
            54 : "\u897f\u85cf",
            61 : "\u9655\u897f",
            62 : "\u7518\u8083",
            63 : "\u9752\u6d77",
            64 : "\u5b81\u590f",
            65 : "\u65b0\u7586",
            71 : "\u53f0\u6e7e",
            81 : "\u9999\u6e2f",
            82 : "\u6fb3\u95e8",
            91 : "\u56fd\u5916"
        } [parseInt(a.substr(0, 2))]) return ! 1;
        switch (a.length) {
        case 15:
            if ("111111111111111" == a) return ! 1;
            ereg = 0 == (parseInt(a.substr(6, 2)) + 1900) % 4 || 0 == (parseInt(a.substr(6, 2)) + 1900) % 100 && 0 == (parseInt(a.substr(6, 2)) + 1900) % 4 ? /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/: /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
            return ereg.test(a) ? !0 : !1;
        case 18:
            ereg = 0 == parseInt(a.substr(6, 4)) % 4 || 0 == parseInt(a.substr(6, 4)) % 100 && 0 == parseInt(a.substr(6, 4)) % 4 ? /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/: /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
            return ereg.test(a) ? (a = 7 * (parseInt(b[0]) + parseInt(b[10])) + 9 * (parseInt(b[1]) + parseInt(b[11])) + 10 * (parseInt(b[2]) + parseInt(b[12])) + 5 * (parseInt(b[3]) + parseInt(b[13])) + 8 * (parseInt(b[4]) + parseInt(b[14])) + 4 * (parseInt(b[5]) + parseInt(b[15])) + 2 * (parseInt(b[6]) + parseInt(b[16])) + 1 * parseInt(b[7]) + 6 * parseInt(b[8]) + 3 * parseInt(b[9]), a = "10X98765432".substr(a % 11, 1), a == b[17] ? !0 : !1) : !1;
        default:
            return ! 1;
        }
    }
    function t() {
        var a = $("source");
        a || (a = z("xd_source"));
        a && r("xd_source", a, 365, "/", ".xd.com");
        return a;
    }
    function ia(a) {
        l = null;
        b.ajax({
            url: k + "/users/logoutService",
            dataType: "jsonp",
            success: function() {
                b.isFunction(a) ? ja(function() {
                    a(l);
                }) : ja();
            }
        });
    }
    function ka(a, e) {
        a = b.trim(a);
        if (b.isFunction(e)) {
            var c = !1,
            d = "2-20\u4e2a\u4e2d\u82f1\u6587\u6570\u5b57";
            T != a ? (T = a, 2 > a.length ? (p = c = !1, s = d = "\u7528\u6237\u540d\u592a\u77ed\uff0c\u81f3\u5c112\u4f4d", e(j(c, d))) : 20 < a.length ? (p = c = !1, s = d = "\u7528\u6237\u540d\u592a\u957f\uff0c\u6700\u591a20\u4f4d", e(j(c, d))) : a.match(/^[\w_\u3300-\u9fff\uf900-\ufaff]{2,20}$/) ? b.ajax({
                url: k + "/users/check_username",
                data: {
                    username: a
                },
                dataType: "jsonp",
                success: function(a) {
                    switch (a.toString()) {
                    case "1":
                        c = !0;
                        d = "\u7528\u6237\u540d\u53ef\u7528";
                        break;
                    case "-1":
                        c = !1;
                        d = "2-20\u4e2a\u4e2d\u82f1\u6587\u6570\u5b57";
                        break;
                    case "-3":
                        c = !1,
                        d = "\u7528\u6237\u540d\u5df2\u88ab\u6ce8\u518c";
                    }
                    p = c;
                    s = d;
                    e(j(c, d));
                }
            }) : (p = c = !1, s = d = "\u5305\u542b\u975e\u6cd5\u5b57\u7b26\u8bf7\u91cd\u65b0\u8f93\u5165", e(j(c, d)))) : e(j(p, s));
        }
    }
    function la(a) {
        a = b.trim(a);
        if (6 > a.length) return q = a,
        j(!1, "\u5bc6\u7801\u592a\u77ed\uff0c\u6700\u5c116\u4f4d");
        if (20 < a.length) return q = a,
        j(!1, "\u5bc6\u7801\u592a\u957f\uff0c\u6700\u591a20\u4f4d");
        q = a;
        return j(!0, "\u5bc6\u7801\u53ef\u7528")
    }
    function ma(a) {
        a = b.trim(a);
        return null == q || "" == q ? j(!1, "\u8bf7\u522b\u5fd8\u8bb0\u4e0a\u9762\u7684\u5bc6\u7801") : a != q ? j(!1, "\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u81f4") : j(!0, "\u5bc6\u7801\u4e00\u81f4")
    }
    function na(a, e) {
        a = b.trim(a);
        if (b.isFunction(e)) {
            var c = !1,
            d = "\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u7535\u5b50\u90ae\u7bb1";
            6 < a.length && a.match(/^[A-Z0-9._%+\-]*[A-Z0-9]@(?:[A-Z0-9\-]+\.)+[A-Z]{2,4}$/i) && (c = !0, d = "\u90ae\u7bb1\u53ef\u7528");
            e(j(c, d));
        }
    }
    function oa(a) {
        a = b.trim(a);
        return /^[\u3300-\u9fff\uf900-\ufaff]{2,}$/.test(a) ? j(!0, "\u771f\u5b9e\u59d3\u540d") : j(!1, "\u8bf7\u8f93\u5165\u771f\u5b9e\u59d3\u540d")
    }
    function pa(a) {
        a = b.trim(a);
        return wa(a) ? j(!0, "\u6709\u6548\u8eab\u4efd\u8bc1") : j(!1, "\u8bf7\u8f93\u516515\u621618\u4f4d\u6709\u6548\u8eab\u4efd\u8bc1")
    }
    function z(a) {
        for (var a = a + "=",
        b = document.cookie.split(";"), c = 0; c < b.length; c++) {
            for (var d = b[c];
            " " == d.charAt(0);) d = d.substring(1, d.length);
            if (0 == d.indexOf(a)) return decodeURIComponent(d.substring(a.length, d.length));
        }
        return null;
    }
    function r(a, b, c, d, h) {
        if (a) {
            var g = "";
            c && (g = new Date, g.setTime(g.getTime() + 864E5 * c), g = "; expires=" + g.toGMTString());
            document.cookie = a + "=" + encodeURIComponent(b) + g + (";path=" + (d || "/")) + (h ? ";domain=" + h: "")
        }
    }
    function O(a, b, c) {
        r(a, "", -100, b, c);
    }
    function $(a) {
        a = a.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        a = RegExp("[\\?&]" + a + "=([^&#]*)").exec(window.location.href);
        return null == a ? "": decodeURIComponent(a[1].replace(/\+/g, " "));
    }
    function qa(a, e, c, d, h) {
        document.domain = "xd.com";
        window.open(a, c || "open_window", "width=" + (d || 500) + ",height=" + (h || 350) + ",menubar=0,scrollbars=1, resizable=1,status=1,titlebar=0,toolbar=0,location=0");
        window.child_close_handler = function(a) {
            a && r("xd[user]", a, 30, "/", ".xd.com");
            b.isFunction(e) && ra(e);
        };
    }
    function ga(a) {
        b.isFunction(a) || (a = function() {
            window.location.reload();
        });
        qa(k + "/oauth/qq_login", a, "TencentLogin", 694, 600);
        return ! 1;
    }
    function ra(a) {
        b.ajax({
            url: k + "/users/sync_login",
            dataType: "jsonp",
            success: function(e) {
                e && b("body").append("<div>" + e + "</div>");
                b.isFunction(a) && setTimeout(function() {
                    try {
                        a();
                    } catch(b) {}
                },
                100);
            }
        });
    }
    function ja(a) {
        b.ajax({
            url: k + "/users/sync_logout",
            dataType: "jsonp",
            success: function(e) {
                b("body").append("<div>" + e + "</div>");
                b.isFunction(a) && setTimeout(function() {
                    try {
                        a();
                    } catch(b) {}
                },
                100);
            }
        });
    }
    function aa(a, e, c) {
        I || (I = !0, xd.getUser(function(d) {
            0 < d.id && (b.isFunction(a) ? a(d) : e ? window.location = e: c ? window.location = xd.getPlayUrl(c) : document.referrer && (window.location = document.referrer));
            I = !1;
        },
        c));
    }
    function P(a) {
        a && "undefined" != typeof _gaq && _gaq.push(["_trackPageview", a]);
    }
    var k = "http://www.rekoe.com";
    0 == window.location.host.indexOf("local.") ? k = "http://local.www.xd.com": 0 < window.location.host.indexOf("office.") && (k = "http://www.office.xd.com");
    var l = null,
    J = [],
    K = !1;
    T = void 0;
    p = void 0;
    s = void 0;
    q = void 0;
    var L = [],
    b;
    "undefined" == typeof jQuery ? va("http://web.xdcdn.net/xd/js/jquery.js",
    function() {
        U();
    }) : U();
    var M, I = !1,
    xa = "/logs/join";
    t();
    return {
        getUser: function(a, e, c) {
            b.isFunction(a) && (l ? a(l) : ("verycd" != c && (c = null), b.ajax({
                url: k + "/users/getuser",
                dataType: "jsonp",
                data: {
                    app: e,
                    site: c
                },
                cache: !1,
                success: function(b) {
                    try {
                        b && 0 < b.id && (l = b, l.returnVisitor && P("/logs/login")),
                        a(b);
                    } catch(c) {}
                }
            })));
        },
        login: function(a, e, c, d, h, g, j, Q, f) {
            if (b.isFunction(d) && b.isFunction(h)) {
                if (l) {
                    if (l.username == a) {
                        d(l);
                        return
                    }
                    ia();
                }
                if (!K) if (a = b.trim(a), e = b.trim(e), a) if (e) {
                    "xd" != j && "verycd" != j && (j = "xd");
                    "tdyx" != g && "sxd" != g && "sssg" != g && (g = null);
                    Q = Q || null;
                    K = !0;
                    var o = setTimeout("logging=false;", 1E4);
                    b.ajax({
                        url: k + "/users/loginService",
                        dataType: "jsonp",
                        data: {
                            "user.username": a,
                            "user.password": e,
                            "user.remember_me": Boolean(c),
                            "user.site": j,
                            app: g,
                            captcha: Q
                        },
                        cache: !1,
                        success: function(a) {
                            K = !1;
                            clearTimeout(o);
                            if (a && 0 < a.id) if (!0 === a.twoauth) {
                                a = k + "/security/twoauth";
                                if ("/game/index.html" == window.location.pathname || "/game/" == window.location.pathname) a = k + "/security/twoauth_s";
                                "undefined" != typeof f && (a += "?redirect=" + f);
                                window.location = a;
                            } else P("/logs/login"),
                            l = a,
                            ra(function() {
                                d(l);
                            });
                            else try {
                                h(a);
                            } catch(b) {}
                        }
                    });
                } else h({
                    error: "\u5bc6\u7801\u4e3a\u7a7a"
                });
                else h({
                    error: "\u7528\u6237\u540d\u4e3a\u7a7a"
                });
            }
        },
        logout: ia,
        getServers: function(a, e) {
            a && b.isFunction(e) && (J[a] ? e(J[a]) : b.ajax({
                url: k + "/games/getservers/" + a,
                dataType: "jsonp",
                success: function(b) {
                    b && (J[a] = b);
                    e(b);
                }
            }));
        },
        getPlayUrl: function(a, b) {
            if (!a) return ! 1;
            var c = k + "/games/play?app=" + a;
            b && (c += "&server=" + b);
            return c;
        },
        checkUsername: ka,
        checkPassword: la,
        checkConfirm: ma,
        checkEmail: na,
        checkRealname: oa,
        checkRealid: pa,
        register: function(a, e, c, d, h, g, l, Q, f) {
            function o(o, v) {
                if (u) if (v.valid) {
                    n[o] = !0;
                    for (i in n) if (!n[i]) return;
                    var w = {
                        username: a,
                        password: e,
                        confirm: c
                    };
                    d && (w.email = d);
                    h && (w.realname = h);
                    g && (w.realid = g);
                    l && (w.captcha = l);
                    var R = t();
                    R && (w.source = R);
                    b.ajax({
                        url: k + "/users/registerService",
                        success: function(a) {
                            0 >= a.user ? f(j(!1, a.error)) : (P(xa), R && O("xd_source", "/", ".xd.com"), Q(a))
                        },
                        cache: !1,
                        data: w,
                        dataType: "jsonp"
                    })
                } else u = !1,
                f(v)
            }
            a = b.trim(a);
            e = b.trim(e);
            c = b.trim(c);
            d = b.trim(d);
            h = b.trim(h);
            g = b.trim(g);
            l = b.trim(l);
            if (b.isFunction(Q) && b.isFunction(f)) {
                var n = {
                    username: !1,
                    password: !1,
                    confirm: !1
                };
                d && (n.email = !1);
                h && (n.realname = !1);
                g && (n.realid = !1);
                var u = !0;
                ka(a,
                function(a) {
                    o("username", a);
                });
                o("password", la(e));
                o("confirm", ma(c));
                d && na(d,
                function(a) {
                    o("email", a);
                });
                h && o("realname", oa(h));
                g && o("realid", pa(g));
            }
        },
        getCookie: z,
        setCookie: r,
        deleteCookie: O,
        clearCookie: function(a, b) {
            for (var c = [], d = document.cookie.split(";"), h = 0; h < d.length; h++) {
                for (var g = d[h];
                " " == g.charAt(0);) g = g.substring(1, g.indexOf("="));
                c.push(g);
            }
            for (h in c) O(c[h], a, b);
        },
        getSite: function() {
            value = z("uidsite");
            return "xd" == value || "verycd" == value ? value: "xd";
        },
        setSite: function(a) {
            "xd" != a && "verycd" != a && (a = "xd");
            r("uidsite", a, 3E3, "/", ".xd.com");
        },
        bookmarkMe: function(a, b) {
            a = a || "http://www.com";
            b = b || "\u5fc3\u52a8\u6e38\u620f";
            window.sidebar ? window.sidebar.addPanel(b, a, "") : document.all ? window.external.AddFavorite(a, b) : alert("\u8bf7\u6309 Ctrl + D \u52a0\u5165\u6536\u85cf\u5939")
        },
        getParameterByName: $,
        capitaliseFirstLetter: function(a) {
            return a.charAt(0).toUpperCase() + a.slice(1);
        },
        setDomain: function(a) {
            k = a;
        },
        getPlayedHistory: function(a, e) {
            a && b.isFunction(e) && (L[a] ? e(L[a]) : b.ajax({
                url: k + "/games/history/" + a,
                dataType: "jsonp",
                success: function(b) {
                    if (b) {
                        for (var d in b) b[d] = b[d].UserPlayHistory;
                        L[a] = b;
                    }
                    e(b);
                }
            }));
        },
        getCaptchaUrl: function() {
            return k + "/security/captcha/" + Math.random()
        },
        qq_login: ga,
        qq_redirect: function(a) {
            a || (a = window.location.href);
            window.location.href = k + "/oauth/qq_redirect?return=" + a
        },
        weibo_login: function(a) {
            b.isFunction(a) || (a = function() {
                window.location.reload();
            });
            qa(k + "/oauth/weibo_login", a, "WeiboLogin", 585, 422);
            return ! 1;
        },
        getdomain: function() {
            return k;
        },
        check_password_strongness: function(a) {
            return ! a ? !1 : -1 < b.inArray(a, "000000,111111,11111111,112233,123123,123321,123456,12345678,654321,666666,888888,abcdef,abcabc,abc123,a1b2c3,aaa111,123qwe,qwerty,qweasd,admin,password,p@ssword,passwd,iloveyou,5201314,123456,111111,123456789,123123,a636654,3727997,qqq111,a123456,13e4e5sd,aa123456,100200,131313,5897037,2281538,5201314,123abc,196299,456852,wolf8637,qqqqqq,js77777,a520520,qq111111,1qaz2wsx,11111111,lnb5552436,111333,qq123123,xiaohe,216612,zz123456,123456aa,e10adc3949ba59abbe56,556656,az123456,anglang,qq666666,q1w2e3r4,25991314,qq11111,zzzxxx,1989617,4782633,112233abc,qqqaaa,830415,ningbo,336699,wishing,qw5555,1234567,198723,123321,xiao801013,19790219,yuyang11,521326,1314520,abc123456,123456789k,jiangji,l138071,abu131060,484848,dircls128,yuzhiyuan1,a42176488,deng1234,wy1981813,555444,zz12369,uni0728,7758521,5501146,159357,800528sd,tang520qin,123aa123,li222222,hb000000,5211314,456456,123550,123456a,liulan860420,zxcvbnm,tyu78ju,sd123456,121212,666666,1985516,zeng1013,popo999,asdasd,7878456,147258,woaini".split(",")) ? 1 : a.match(/^\d+$/) || a.match(/^[a-z]+$/i) ? 4 : a.match(/^[0-9a-z]+$/i) ? 2 : 3
        },
        getSource: t,
        getCity: function(a) {
            M ? a(M) : b.ajax({
                url: k + "/users/getCity",
                dataType: "jsonp",
                success: function(b) {
                    M = b;
                    a(M);
                }
            });
        },
        registerForm: function(a, e, c, d, h, g, j, l, f) {
            function o() {
                xd.checkUsername(C.val(),
                function(a) {
                    m.username = a.valid;
                    var b = a.msg;
                    "undefined" != typeof f[a.msg] && (b = f[a.msg]);
                    0 < D.length ? (0 == C.val().length ? D.hide().html(b) : D.show().html(b), D.removeClass("infoOk infoError"), D.addClass(a.valid ? "infoOk": "infoError")) : alert(b);
                    A();
                });
            }
            function n() {
                var a = xd.checkPassword(S.val());
                m.password = a.valid;
                var b = a.msg;
                if (m.password) {
                    var b = S.val(),
                    c = "psstWeak";
                    if (b == C.val()) b = "\u5f3a\u5ea6\u5f31";
                    else {
                        var d = xd.check_password_strongness(b),
                        b = "\u5f3a\u5ea6\u5f31",
                        c = "psstWeak";
                        4 == d && (b = "\u5f3a\u5ea6\u4f4e", c = "psstLow");
                        2 == d ? (b = "\u5f3a\u5ea6\u4e2d", c = "psstNormal") : 3 == d && (b = "\u5f3a\u5ea6\u9ad8", c = "psstHigh")
                    }
                }
                "undefined" != typeof f[b] && (b = f[b]);
                0 < B.length ? (0 == S.val().length ? B.hide() : B.html(b).show(), B.removeClass("infoOk infoError psstWeak psstNormal psstHigh"), B.addClass(a.valid ? "infoOk": "infoError"), B.addClass(c)) : alert(b);
                0 < N.length && u();
                A()
            }
            function u() {
                var a = xd.checkConfirm(N.val());
                m.confirm = a.valid;
                var b = a.msg;
                "undefined" != typeof f[a.msg] && (b = f[a.msg]);
                0 < E.length ? (0 == N.val().length ? E.hide() : E.html(b).show(), E.removeClass("infoOk infoError"), E.addClass(a.valid ? "infoOk": "infoError")) : alert(b);
                A()
            }
            function q() {
                var a = "\u8bf7\u518d\u6b21\u8f93\u5165\u5bc6\u7801\uff0c\u786e\u4fdd\u5bc6\u7801\u4e00\u81f4";
                "undefined" != typeof f[a] && (a = f[a]);
                E.removeClass("infoOk infoError");
                E.html(a);
                E.show()
            }
            function v() {
                var a = xd.checkRealname(V.val());
                m.realname = a.valid;
                var b = a.msg;
                "undefined" != typeof f[a.msg] && (b = f[a.msg]);
                0 < F.length ? (0 == V.val().length ? F.hide() : F.html(b).show(), F.removeClass("infoOk infoError"), F.addClass(a.valid ? "infoOk": "infoError")) : alert(b);
                A()
            }
            function w() {
                var a = "\u8bf7\u8f93\u5165\u771f\u5b9e\u59d3\u540d";
                "undefined" != typeof f[a] && (a = f[a]);
                F.removeClass("infoOk infoError");
                F.html(a);
                F.show()
            }
            function R() {
                var a = xd.checkRealid(W.val());
                m.realid = a.valid;
                var b = a.msg;
                "undefined" != typeof f[a.msg] && (b = f[a.msg]);
                0 < G.length ? (0 == W.val().length ? G.hide() : G.html(b).show(), G.removeClass("infoOk infoError"), G.addClass(a.valid ? "infoOk": "infoError")) : alert(b);
                A()
            }
            function p() {
                var a = "\u8bf7\u8f93\u5165\u6709\u6548\u8eab\u4efd\u8bc1";
                "undefined" != typeof f[a] && (a = f[a]);
                G.removeClass("infoOk infoError");
                G.html(a);
                G.show()
            }
            function s() {
                m.realname = !1;
                m.realid = !1;
                V.blur(v);
                W.blur(R);
                V.focus(w);
                W.focus(p);
                I.show();
                P.show();
                x.realname = v;
                x.realid = R
            }
            function r() {
                I.hide();
                P.hide()
            }
            function sa() {
                xd.checkEmail(X.val(),
                function(a) {
                    m.email = a.valid;
                    var b = a.msg;
                    "undefined" != typeof f[a.msg] && (b = f[a.msg]);
                    0 < H.length ? (0 == X.val().length ? H.hide() : H.html(b).show(), H.removeClass("infoOk infoError"), H.addClass(a.valid ? "infoOk": "infoError")) : alert(b);
                    A()
                })
            }
            function ya() {
                var a = "\u5e38\u7528\u7684\u90ae\u7bb1\uff0c\u65b9\u4fbf\u60a8\u627e\u56de\u5bc6\u7801";
                "undefined" != typeof f[a] && (a = f[a]);
                H.removeClass("infoOk infoError");
                H.html(a);
                H.show()
            }
            function ta() {
                m.email = !1;
                X.blur(sa);
                X.focus(ya);
                x.email = sa;
                J.show()
            }
            function za() {
                J.hide()
            }
            function M() {
                m.agreement = L.is(":checked");
                m.agreement ? 0 < ba.length && ba.hide() : ("undefined" != typeof f["\u8bf7\u540c\u610f\u7528\u6237\u534f\u8bae"] && (msg = f["\u8bf7\u540c\u610f\u7528\u6237\u534f\u8bae"]), 0 < ba.length ? ba.html("\u8bf7\u540c\u610f\u7528\u6237\u534f\u8bae").addClass("infoError").show() : alert("\u8bf7\u540c\u610f\u7528\u6237\u534f\u8bae"));
                A()
            }
            function ua() {
                m.agreement = L.is(":checked");
                L.change(M);
                K.show();
                x.agreement = M
            }
            function T() {
                K.hide()
            }
            function t() {
                var a = ca.val();
                m.captcha = 4 == a.length;
                m.captcha ? 0 < da.length && da.hide() : (a = "\u8bf7\u8f93\u51654\u4f4d\u9a8c\u8bc1\u7801", "undefined" != typeof f[a] && (a = f[a]), 0 < da.length ? da.html(a).addClass("infoError").show() : alert(a));
                A()
            }
            function z() {
                Y.is(":hidden") && (m.captcha = !1, ea.attr("src", xd.getCaptchaUrl()), ea.click(function() {
                    ea.attr("src", xd.getCaptchaUrl())
                }), ca.blur(t), ca.enter(function() {
                    t()
                }), Y.show(), x.captcha = t, Y.trigger("isVisible"))
            }
            function A(c) {
                "undefined" == typeof c && (c = !1);
                var d = !0,
                e = !0,
                f;
                for (f in m) if (!m[f]) {
                    d = !1;
                    if (!c) break;
                    var g = a + " ." + f + "Info";
                    if (!b(g).hasClass("infoError") && b.isFunction(x[f])) x[f]();
                    var h = a + " ." + f + "Input";
                    if (null == b(h).val() || "" == b(h).val()) {
                        b(g).removeClass("infoOk infoError");
                        switch (f) {
                        case "username":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u8f93\u5165\u7528\u6237\u540d");
                            break;
                        case "password":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u8f93\u5165\u5bc6\u7801");
                            break;
                        case "confirm":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u786e\u8ba4\u5bc6\u7801");
                            break;
                        case "realid":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u8f93\u5165\u8eab\u4efd\u8bc1");
                            break;
                        case "realname":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u8f93\u5165\u771f\u5b9e\u59d3\u540d");
                            break;
                        case "email":
                            b(g).html("\u8bf7\u522b\u5fd8\u8bb0\u8f93\u5165\u7535\u5b50\u90ae\u7bb1")
                        }
                        b(g).show()
                    }
                    e && (e = !1, b(h).focus())
                }
                return d
            }
            aa(e, c, d);
            var O = b(a);
            if (0 != O.length) {
                var Z = b(a + " .usernameField"),
                C = b(a + " .usernameInput"),
                D = b(a + " .usernameInfo");
                if (0 != Z.length) {
                    var Z = b(a + " .passwordField"),
                    S = b(a + " .passwordInput"),
                    B = b(a + " .passwordInfo");
                    if (0 != Z.length) {
                        var Z = b(a + " .confirmField"),
                        N = b(a + " .confirmInput"),
                        E = b(a + " .confirmInfo"),
                        I = b(a + " .realnameField"),
                        V = b(a + " .realnameInput"),
                        F = b(a + " .realnameInfo"),
                        P = b(a + " .realidField"),
                        W = b(a + " .realidInput"),
                        G = b(a + " .realidInfo"),
                        J = b(a + " .emailField"),
                        X = b(a + " .emailInput"),
                        H = b(a + " .emailInfo"),
                        K = b(a + " .agreementField"),
                        L = b(a + " .agreementInput"),
                        ba = b(a + " .agreementInfo"),
                        Y = b(a + " .captchaField"),
                        ca = b(a + " .captchaInput"),
                        ea = b(a + " .captchaImg"),
                        da = b(a + " .captchaInfo");
                        b(a + " .submitInput");
                        var ha = b(a + " .submitInfo"),
                        U = b(a + " .qqField"),
                        m = {
                            username: !1,
                            password: !1
                        },
                        x = {};
                        f || (f = {});
                        C.blur(o);
                        C.focus(function() {
                            var a = "2-20\u4e2a\u4e2d\u82f1\u6587\u5b57\u7b26\u3001\u6570\u5b57\u6216\u4e0b\u5212\u7ebf";
                            "undefined" != typeof f[a] && (a = f[a]);
                            D.removeClass("infoOk infoError");
                            D.html(a);
                            D.show()
                        });
                        x.username = o;
                        S.blur(n);
                        S.focus(function() {
                            var a = "6-20\u4f4d\u5b57\u7b26(\u5b57\u6bcd,\u6570\u5b57,\u7b26\u53f7)";
                            "undefined" != typeof f[a] && (a = f[a]);
                            B.removeClass("infoOk infoError psstWeak psstNormal psstHigh");
                            B.html(a);
                            B.show()
                        });
                        x.password = n;
                        0 < Z.length && (m.confirm = !1, N.blur(u), N.focus(q), N.enter(function() {
                            u()
                        }), x.confirm = u);
                        var y = [];
                        0 < I.length && (h ? s() : y.push([s, r]));
                        0 < J.length && (g ? ta() : y.push([ta, za]));
                        0 < K.length && (j ? ua() : y.push([ua, T]));
                        0 < Y.length && (l ? z() : b.ajax({
                            url: k + "/users/checkClientIpCount",
                            dataType: "jsonp",
                            success: function(a) {
                                1 !== a && z()
                            }
                        }));
                        if (0 < y.length) if (window.location.search.match(/(\?|&)ads(=|$|&)/i)) for (var $ in y) y[$][1]();
                        else xd.getCity(function(a) {
                            a = a.substring(0, 2);
                            if ("\u4e0a\u6d77" == a || "\u5317\u4eac" == a) for (var b in y) y[b][0]();
                            else for (b in y) y[b][1]()
                        });
                        var fa = !1;
                        O.submit(function() {
                            if (fa || !A(!0)) return ! 1;
                            fa = !0;
                            xd.register(C.val(), S.val(), N.val(), X.val(), V.val(), W.val(), ca.val(),
                            function(a) {
                                fa = !1;
                                b.isFunction(e) ? e(a) : c ? window.location = c: d ? window.location = xd.getPlayUrl(d) : document.referrer && (window.location = document.referrer)
                            },
                            function(a) {
                                fa = !1;
                                var b = a.msg;
                                "undefined" != typeof f[b] && (b = f[b]);
                                "\u9a8c\u8bc1\u7801\u9519\u8bef" == a.msg && z();
                                Y.is(":visible") && ea.click();
                                0 < ha.length ? (ha.html(b).show(), ha.addClass("infoError")) : alert(msg)
                            });
                            return ! 1
                        });
                        C.is(":visible") && C.focus();
                        0 < U.length && U.click(function() {
                            ga(function() {
                                aa(e, c, d)
                            })
                        })
                    }
                }
            }
        },
        loginForm: function(a, e, c, d, h, g, j) {
            function k() {
                v.is(":hidden") && (q.click(k), v.show());
                q.attr("src", xd.getCaptchaUrl())
            }
            function f(a) {
                "undefined" != typeof g[a] && (a = g[a]);
                0 < p.length ? (p.html(a).addClass("infoError").show(), p.trigger("isInfoError")) : alert(a)
            }
            aa(e, c, d);
            var l = b(a);
            if (0 != l.length) {
                var n = b(a + " .usernameInput");
                if (0 != n.length) {
                    var u = b(a + " .passwordInput");
                    if (0 != u.length) {
                        var s = b(a + " .rememberInput"),
                        v = b(a + " .captchaField"),
                        w = b(a + " .captchaInput"),
                        q = b(a + " .captchaImg");
                        b(a + " .submitInput");
                        var p = b(a + " .submitInfo"),
                        t = b(a + " .qqField"),
                        z = b(a + " .siteInput").val();
                        g || (g = {});
                        0 < v.length && h && k();
                        var r = !1;
                        l.submit(function() {
                            if (r) return ! 1;
                            var a = n.val();
                            if (!a) return f("\u8bf7\u8f93\u5165\u7528\u6237\u540d"),
                            !1;
                            var g = u.val();
                            if (!g) return f("\u8bf7\u8f93\u5165\u5bc6\u7801"),
                            !1;
                            var h = "";
                            if (v.is(":visible") && (h = w.val(), 4 != h.length)) return f("\u8bf7\u8f93\u51654\u4f4d\u9a8c\u8bc1\u7801"),
                            !1;
                            var l = s.is(":checked");
                            r = !0;
                            j || (j = window.location.href);
                            xd.login(a, g, l,
                            function(a) {
                                r = !1;
                                b.isFunction(e) ? e(a) : c ? window.location = c: d ? window.location = xd.getPlayUrl(d) : document.referrer && (window.location = document.referrer)
                            },
                            function(a) {
                                r = !1;
                                a.needCaptcha && k();
                                f(a.error)
                            },
                            d, z, h, j);
                            return ! 1
                        });
                        n.is(":visible") && n.focus();
                        0 < t.length && t.click(function() {
                            ga(function() {
                                aa(e, c, d)
                            })
                        })
                    }
                }
            }
        }
    }
} ();