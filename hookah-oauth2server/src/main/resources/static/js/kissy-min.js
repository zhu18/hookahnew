KISSY.add("register/components/mobile", function (a) {
    var b = a.require("event"), c = a.require("register/components/xerror"), d = window.TRLang || {}, e = {
        MOBILE_EMPTY: d.ERROR_GENERAL_MOBILEPHONE_EMPTY || "请输入手机号码",
        MOBILE_INVALID: d.ERROR_GENERAL_MOBILEPHONE_MALFORM || "手机号码格式不正确，请重新输入"
    }, f = function (b) {
        return this instanceof f ? (b = b || {}, this.elArea = b.elArea && a.all(b.elArea), this.elAreaCode = b.elAreaCode && a.all(b.elAreaCode), this.elMobile = b.elMobile && a.all(b.elMobile), void this._init()) : new f(b)
    };
    return a.augment(f, b.Target, {
        _init: function () {
            this._initArea()
        }, _initArea: function () {
            if (this.elArea && this.elMobile) {
                var a = this;
                this.elArea.on("change", function (b) {
                    a.elAreaCode.text("+" + a.getAreaCode()), a.pattern = a.getPattern();
                    var c = {code: a.getAreaCode()};
                    a.fire("change", c)
                }), this.elArea.fire("change")
            }
        }, validate: function () {
            var a, b = Q.defer(), d = this.elMobile.val(), f = this.getAreaCode();
            return d ? this.pattern.test(f + d) || (a = "MOBILE_INVALID") : a = "MOBILE_EMPTY", a ? b.reject(new c(a, e[a])) : b.resolve(), b.promise
        }, getAreaCode: function () {
            return a.all(this.elArea[0].options[this.elArea[0].selectedIndex]).attr("data-code") || ""
        }, getPattern: function () {
            var b = a.all(this.elArea[0].options[this.elArea[0].selectedIndex]).attr("data-pattern") || ".+";
            return new RegExp(b)
        }
    }), f
}, {requires: ["node", "event", "register/components/xerror"]});