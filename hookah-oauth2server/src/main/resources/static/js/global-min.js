KISSY.add("register/components/passwordtip", function (a) {
    var b = a.require("event"), c = (a.require("dom"), window.TRLang || {}), d = {
        low: c.pwd_level_low || "低",
        medium: c.pwd_level_medium || "中",
        high: c.pwd_level_high || "高"
    }, e = {ok: "&#x3447;", error: "&#x3432;", normal: "&#xe611;"}, f = function (b) {
        return this instanceof f ? (b = b || {}, this.refEl = b.refEl && a.one(b.refEl), this.offset = a.isObject(b.offset) ? b.offset : {}, this.offset.left = this.offset.left || 0, this.offset.top = this.offset.top || 0, void this._init()) : new f(b)
    };
    return a.augment(f, b.Target, {
        _init: function () {
        }, _create: function () {
            this._CREATED || (this._CREATED = !0, this.el = a.all("#J_PwdTip"), a.one("body").append(this.el))
        }, show: function () {
            var a = this.refEl.offset();
            this._create(), this.el.show().css({
                position: "absolute",
                left: a.left + this.offset.left,
                top: a.top - this.el.height() / 2 + this.offset.top
            })
        }, hide: function () {
            this.el.hide()
        }, update: function (b) {
            for (var c in b)this.updateItem(c, b[c]);
            a.all(".pw-strength-bar").attr({"class": "pw-strength-bar pw-strength-bar-" + b.strength}).all("em").html([d.low, d.medium, d.high][b.strength - 1])
        }, updateItem: function (b, c) {
            var d, c = c === !0 ? "ok" : c === !1 ? "error" : "normal", f = e[c];
            switch (b) {
                case"length":
                    d = a.all(".pw-rule-length");
                    break;
                case"legal":
                    d = a.all(".pw-rule-legal");
                    break;
                case"multi":
                    d = a.all(".pw-rule-multi")
            }
            d && d.all(".iconfont").attr({"class": "iconfont pw-icon-" + c}).html(f)
        }, reset: function () {
            this.updateItem("length"), this.updateItem("legal"), this.updateItem("multi"), a.all(".pw-strength-bar").attr({"class": "pw-strength-bar"}).all("em").html("")
        }
    }), f
}, {requires: ["event", "dom"]});