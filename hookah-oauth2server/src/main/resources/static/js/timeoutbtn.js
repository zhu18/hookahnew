KISSY.add("register/components/passwordrule", function () {
    return {
        regex: {
            illegal: /[^-+=|,0-9a-zA-Z!@#$%^&*?_.~+\/\\(){}\[\]<>]/, //不合法的
            allNumber: /^\d+$/, //全部是数字
            allLetter: /^[a-zA-Z]+$/, //全部是字母
            allCharacter: /^[-+=|,!@#$%^&*?_.~+\/\\(){}\[\]<>]+$/,//全部是字符
            allSame: /^([\s\S])\1*$/,//全部相同
            upperLetter: /[A-Z]/,//匹配大写
            lowerLetter: /[a-z]/,//匹配小写
            number: /\d/g,//匹配数字
            character: /[-+=|,!@#$%^&*?_.~+\/\\()|{}\[\]<>]/     //匹配字符
        }, score: function (a) {
            var b = 0;
            if (this.isIllegal(a))return b;
            var c = this.size(a);
            4 >= c ? b += 5 : c > 4 && 8 > c ? b += 10 : c >= 8 && (b += 25);
            var d = this.hasLowerAndUpperLetter(a), e = this.hasLetter(a);
            d ? b += 20 : e && (b += 10);
            var f = this.hasNumber(a);
            f >= 3 ? b += 20 : f && (b += 10);
            var g = this.hasCharacter(a);
            return g >= 3 ? b += 25 : g && (b += 10), d && f && g ? b += 10 : e && f && g ? b += 5 : (e && f || e && g || f && g) && (b += 2), b
        }, level: function (a) {
            return Math.floor(this.score(a) / 10)
        }, size: function (a) {
            return a.length
        }, isIllegal: function (a) {
            return !!a.match(this.regex.illegal)
        }, isAllNumber: function (a) {
            return !!a.match(this.regex.allNumber)
        }, isAllLetter: function (a) {
            return !!a.match(this.regex.allLetter)
        }, isAllSame: function (a) {
            return !!a.match(this.regex.allSame)
        }, hasNumber: function (a) {
            return (a.match(this.regex.number) || []).length
        }, hasLetter: function (a) {
            return !!a.match(this.regex.lowerLetter) || !!a.match(this.regex.upperLetter)
        }, hasLowerAndUpperLetter: function (a) {
            return !!a.match(this.regex.lowerLetter) && !!a.match(this.regex.upperLetter)
        }, hasNumberAndLetter: function (a) {
            return !(!a.match(this.regex.number) || !a.match(this.regex.lowerLetter) && !a.match(this.regexp.upperLetter))
        }, hasCharacter: function (a) {
            return (a.match(this.regex.character) || []).length
        }
    }
});