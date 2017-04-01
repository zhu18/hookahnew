KISSY.use("overlay, event, node, dom, menu,register/components/timeoutbtn, register/components/xerror, register/components/password, register/components/repassword, register/components/message, register/utils/messages, register/components/similar,register/components/mobile,register/utils/checkenable", function (a) {
  var b, c = window.TRLang || {}, d = window.TRConfig, e = a.require("overlay"), f = a.require("register/components/password"), g = a.require("register/components/repassword"), h = a.require("register/components/nicksize"), i = a.require("register/components/similar"), j = a.require("register/components/nick"), k = a.require("register/components/xerror"), l = a.require("register/components/message"), m = a.require("register/utils/messages"), n = a.require("register/components/timeoutbtn"), o = a.require("register/utils/checkenable"), p = f({el: "#J_Password"}), q = g({
    elPassword: "#J_Password",
    elRePassword: "#J_RePassword"
  }), r = m({
    messages: {
      mobile: l({el: "#J_MsgMobile", offsetEl: "#J_Mobile", offset: {top: 8}}),
      resendCode: l({el: "#J_MsgResendCode"}),
      password: l({el: "#J_MsgPassword", offsetEl: "#J_Password", offset: {top: 8}}),
      repassword: l({el: "#J_MsgRePassword", offsetEl: "#J_RePassword", offset: {top: 8}}),
      infoForm: l({el: "#J_MsgInfoForm"}),
      nick: l({el: "#J_MsgNick", offsetEl: "#J_Nick", offset: {top: 8}})
    }
  }), s = new e.Dialog({srcNode: "#J_MobileCheck", mask: !0, align: {node: "#J_InfoForm", points: ["tc", "tc"]}});
  s.center().render(), s.on("hide", function () {
    window.nc && window.nc.reset()
  });
  var t = h({el: "#J_Nick", container: "#J_NickSize"}), u = j({el: "#J_Nick"}).on("select", function () {
    a.one("#J_Nick").removeClass("err-input"), t.update()
  });
  a.all("#J_Nick").on("focus", function () {
    var b = a.one("#J_Nick").attr("data-outer_placeholder");
    b && r.messages.nick.set({type: "info", display: "inline", content: c[b]}).show()
  }).on("blur", function () {
    r.messages.infoForm.hide();
    var b = a.one("#J_Password").val(), d = a.one("#J_Nick").val();
    return b && d && i(b, d) >= .8 ? (r.messages.password.set({
        type: "error",
        display: "inline",
        content: c.ERROR_PASSWORD_LIKE_NICK || "密码和账户名太相似"
      }).show(), void a.one("#J_Nick").addClass("err-input")) : void u.validate(!0).then(function () {
        a.one("#J_Nick").removeClass("err-input"), r.messages.nick.hide()
      }).fail(function (b) {
        r.messages.nick.set({
          type: "error",
          display: "inline",
          content: b.message || "用户名填写有误"
        }).show(), a.one("#J_Nick").addClass("err-input")
      })
  }), a.all("#J_Password").on("focus", function () {
    r.messages.password.hide(), a.one("#J_Password").removeClass("err-input"), p.showTip()
  }).on("blur", function () {
    r.messages.infoForm.hide(), p.hideTip(), p.validate().then(function (b) {
      r.messages.password.set({
        type: "info",
        display: "inline",
        content: (c.r_ui_pwd_strength || "安全程度") + "：" + p.strengthText()
      }).show(), a.one("#J_Password").removeClass("err-input")
    }).fail(function (b) {
      r.messages.password.set({
        type: "error",
        display: "inline",
        content: b.message || "密码设置不符合要求"
      }).show(), a.one("#J_Password").addClass("err-input")
    }), a.all("#J_RePassword").val() ? q.validate().then(function () {
        r.messages.repassword.hide(), a.one("#J_RePassword").removeClass("err-input")
      }).fail(function (b) {
        r.messages.repassword.set({
          type: "error",
          display: "inline",
          content: b.message
        }).show(), a.one("#J_RePassword").addClass("err-input")
      }) : (r.messages.repassword.hide(), a.one("#J_RePassword").removeClass("err-input"))
  }).on("valuechange", function () {
    a.all("#J_Password").val() ? p.updateTip() : p.resetTip()
  }), a.all("#J_RePassword").on("focus", function () {
    var b = a.one("#J_RePassword").attr("placeholder");
    b && r.messages.repassword.set({type: "info", display: "inline", content: b}).show()
  }).on("blur", function () {
    return r.messages.infoForm.hide(), a.all("#J_Password").val() || a.all("#J_RePassword").val() ? void q.validate().then(function () {
        r.messages.repassword.hide(), r.messages.repassword.set({
          display: "inline",
          content: ""
        }).show(), a.one("#J_RePassword").removeClass("err-input")
      }).fail(function (b) {
        r.messages.repassword.set({
          type: "error",
          display: "inline",
          content: b.message
        }).show(), a.one("#J_RePassword").addClass("err-input")
      }) : void r.messages.repassword.hide()
  });
  var v = function () {
    var b, d, e = Q.defer(), f = a.one("#J_Mobile"), g = f.val(), h = new RegExp(f.attr("data-pattern"));
    return g ? h.test(g) || (b = "MOBILE_INVALID", d = "ERROR_GENERAL_MOBILEPHONE_MALFORM") : (b = "MOBILE_EMPTY", d = "ERROR_GENERAL_MOBILEPHONE_EMPTY"), b ? e.reject(new k(b, c[d])) : e.resolve(), e.promise
  };
  a.all("#J_Mobile").on("focus", function () {
    r.messages.mobile.showPlaceHolder({type: "info", display: "inline", el: "#J_Mobile"})
  }).on("blur", function () {
    r.messages.infoForm.hide(), v().then(function () {
      r.messages.mobile.hide(), a.one("#J_Mobile").removeClass("err-input")
    }).fail(function (b) {
      r.messages.mobile.set({
        type: "error",
        content: b && b.message,
        display: "inline"
      }).show(), a.one("#J_Mobile").addClass("err-input")
    })
  });
  var w;
  a.all("#J_InfoForm").on("submit", function (e) {
    e.halt(), w || u.validate(!0).then(function () {
      var b = a.one("#J_Password").val(), d = a.one("#J_Nick").val();
      if (b && d && i(b, d) >= .8)throw new k("SimilarInvalid", c.ERROR_PASSWORD_LIKE_NICK || "密码和账户名太相似")
    }).then(function () {
      return p.validate()
    }).then(function (b) {
      r.messages.password.set({
        display: "inline",
        content: (c.r_ui_pwd_strength || "安全程度") + "：" + p.strengthText()
      }).show(), a.one("#J_Password").removeClass("err-input")
    }).then(function () {
      return q.validate()
    }).then(function () {
      return v()
    }).then(function (b) {
      return Q(a.IO({
        type: "post",
        url: d.sendUrl,
        form: a.one("#J_InfoForm"),
        dataType: "json",
        timeout: 5
      })).fail(function (b) {
        throw a.log(b), new k("NetError", "error")
      })
    }).then(function (d) {
      if (!d || !d[0] || !d[0].success)throw window.nc && window.nc.reset(), new k("MobileSumbitFail", d.message || d[0].reason || "");
      r.hideAllIfNotOk(), d[0] && !d[0].checkPass ? (a.all("#J_MobileCheck").show(), s.show(), a.all("#J_MobileNumber").text(a.all("#J_Mobile").val()), a.all("#J_MobileCode").val(""), r.messages.resendCode.set({
          type: "ok",
          content: c.TIP_REGISTER_CHECKCODE_SENT || "短信校验码已发送至你的手机，请查收"
        }).show(), b || (b = n({el: "#J_BtnMobileCode", timeout: 60, disabledCls: "btn-disabled"})), b.start()) : x(!0)
    }).fail(function (b) {
      switch (b.code) {
        case"PasswordInvalid":
        case"SimilarInvalid":
          r.messages.password.set({
            type: "error",
            display: "inline",
            content: b.message || "密码设置不符合要求"
          }).show(), a.one("#J_Password").addClass("err-input");
          break;
        case"RePasswordInvalid":
          r.messages.repassword.set({
            type: "error",
            display: "inline",
            content: b.message || "密码输入不一致"
          }).show(), a.one("#J_RePassword").addClass("err-input");
          break;
        case"MOBILE_EMPTY":
        case"MOBILE_INVALID":
          r.messages.mobile.set({
            type: "error",
            content: b.message || "手机号码错误，请重新输入",
            display: "inline"
          }).show(), a.one("#J_Mobile").addClass("err-input");
          break;
        case"NoCaptchaFail":
          r.messages.infoForm.set({
            type: "error",
            display: "inline",
            content: b.message || c[b.reason] || "验证码有误！"
          }).show();
          break;
        case"NickInvalid":
        case"NickError":
        case"NICK_LENGTH_INVALID":
          r.messages.nick.set({
            type: "error",
            display: "inline",
            content: b.message || "用户名填写有误"
          }).show(), a.one("#J_Nick").addClass("err-input");
          break;
        default:
          r.messages.infoForm.set({
            type: "error",
            display: "inline",
            content: c[b.message] || c[b.reason] || "信息输入有误，请重新输入！"
          }).show()
      }
    })
  }), a.all("#J_BtnMobileCode").on("click", function () {
    a.IO({
      type: "post", url: d.resendUrl, dataType: "json", timeout: 5, complete: function (d) {
        return d && d.success ? (r.messages.resendCode.set({
            type: "ok",
            content: c.TIP_REGISTER_CHECKCODE_SENT || "短信校验码已发送至你的手机，请查收",
            display: "block"
          }).show(), a.all("#J_MobileCode")[0].focus(), void b.start()) : (r.messages.resendCode.set({
            type: "error",
            content: c[d.reason] || "系统错误，请重试",
            display: "block"
          }).show(), void b.enable())
      }
    })
  }), a.all("#J_MobileCodeForm").on("submit", function (b) {
    b.halt(), a.IO({
      type: "post",
      url: d.checkUrl,
      dataType: "json",
      form: a.one("#J_MobileCodeForm"),
      timeout: 5,
      complete: function (a) {
        return a && a.success ? void x() : void r.messages.resendCode.set({
            type: "error",
            content: c[a.reason] || "系统错误，请稍后重试",
            display: "block"
          }).show()
      }
    })
  });
  var x = function (e) {
    a.IO({
      type: "post",
      url: d.submitUserUrl,
      dataType: "json",
      form: a.one("#J_InfoForm"),
      timeout: 5,
      complete: function (d) {
        if (d && d.success) d.loc && (window.location.href = d.loc); else switch (window.nc && window.nc.reset(), s.hide(), e || (r.messages.resendCode.set({
          type: "error",
          content: c[d.reason] || "系统错误，请稍后重试",
          display: "block"
        }).show(), b.enable()), d.reason) {
          case"ERROR_DUP_NICK_NAME":
            d.commendNicks && u.showSuggest(d.commendNicks), r.messages.nick.set({
              type: "error",
              display: "inline",
              content: c[d.reason] || "用户名填写有误"
            }).show(), a.one("#J_Nick").addClass("err-input");
            break;
          case"ERROR_PASSWORD_LIKE_NICK":
          case"ERROR_MALFORM_NICK_NAME":
          case"ERROR_BANNED_WORDS":
            r.messages.nick.set({
              type: "error",
              display: "inline",
              content: c[d.reason] || "用户名填写有误"
            }).show(), a.one("#J_Nick").addClass("err-input");
            break;
          default:
            r.messages.infoForm.set({type: "error", display: "inline", content: c[d.reason] || "信息输入有误，请重新输入！"}).show()
        }
      }
    })
  };
  o({elCheck: "#J_Agreement", elEnable: "#J_BtnInfoForm"}).on("enable", function () {
    a.one("#J_BtnInfoForm").removeClass("btn-disabled")
  }).on("disable", function () {
    a.one("#J_BtnInfoForm").addClass("btn-disabled")
  })
});