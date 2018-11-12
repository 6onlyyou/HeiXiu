package com.heixiu.errand.MVP.Login

import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Seting.WebActivity
import com.heixiu.errand.MainActivity
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.ApiService
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.CountDownTimerUtils
import com.heixiu.errand.utils.SPUtil
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_phone_login.*
import java.io.UnsupportedEncodingException
import java.util.*

class RegisterActivity : BaseActivity() {
    var mCountDownTimerUtils: CountDownTimerUtils? =null
    var handler: Handler = object : Handler() {
       override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
           if(msg.what==1) {
               ToastUtils.showLong("验证码错误")
           }else{
               val random = Random(System.currentTimeMillis())
               /* 598 百家姓 */
               val Surname = arrayOf(  "Aaron",
                       "Abel",
                       "Abraham",
                       "Adam",
                       "Adrian",
                       "Aidan",
                       "Alva",
                       "Alex",
                       "Alexander",
                       "Alan",
                       "Albert",
                       "Alfred",
                       "Andrew",
                       "Andy",
                       "Angus",
                       "Anthony",
                       "Apollo",
                       "Arnold",
                       "Arthur",
                       "August",
                       "Austin",
                       "Ben",
                       "Benjamin",
                       "Bert",
                       "Benson",
                       "Bill",
                       "Billy",
                       "Blake",
                       "Bob",
                       "Bobby",
                       "Brad",
                       "Brandon",
                       "Brant",
                       "Brent",
                       "Brian",
                       "Brown",
                       "Bruce",
                       "Caleb",
                       "Cameron",
                       "Carl",
                       "Carlos",
                       "Cary",
                       "Caspar",
                       "Cecil",
                       "Charles",
                       "Cheney",
                       "Chris",
                       "Christian",
                       "Christopher",
                       "Clark",
                       "Cliff",
                       "Cody",
                       "Cole",
                       "Colin",
                       "Cosmo",
                       "Daniel",
                       "Denny",
                       "Darwin",
                       "David",
                       "Dennis",
                       "Derek",
                       "Dick",
                       "Donald",
                       "Douglas",
                       "Duke",
                       "Dylan",
                       "Eddie",
                       "Edgar",
                       "Edison",
                       "Edmund",
                       "Edward",
                       "Edwin",
                       "Elijah",
                       "Elliott",
                       "Elvis",
                       "Eric",
                       "Ethan",
                       "Eugene",
                       "Evan",
                       "Enterprise",
                       "Ford",
                       "Francis",
                       "Frank",
                       "Franklin",
                       "Fred",
                       "Gabriel",
                       "Gaby",
                       "Garfield",
                       "Gary",
                       "Gavin",
                       "Geoffrey",
                       "George",
                       "Gino",
                       "Glen",
                       "Glendon",
                       "Hank",
                       "Hardy",
                       "Harrison",
                       "Harry",
                       "Hayden",
                       "Henry",
                       "Hilton",
                       "Hugo",
                       "Hunk",
                       "Howard",
                       "Henry",
                       "Ian",
                       "Ignativs",
                       "Ivan",
                       "Isaac",
                       "Isaiah",
                       "Jack",
                       "Jackson",
                       "Jacob",
                       "James",
                       "Jason",
                       "Jay",
                       "Jeffery",
                       "Jerome",
                       "Jerry",
                       "Jesse",
                       "Jim",
                       "Jimmy",
                       "Joe",
                       "John",
                       "Johnny",
                       "Jonathan",
                       "Jordan",
                       "Jose",
                       "Joshua",
                       "Justin",
                       "Keith",
                       "Ken",
                       "Kennedy",
                       "Kenneth",
                       "Kenny",
                       "Kevin",
                       "Kyle",
                       "Lance",
                       "Larry",
                       "Laurent",
                       "Lawrence",
                       "Leander",
                       "Lee",
                       "Leo",
                       "Leonard",
                       "Leopold",
                       "Leslie",
                       "Loren",
                       "Lori",
                       "Lorin",
                       "Louis",
                       "Luke",
                       "Marcus",
                       "Marcy",
                       "Mark",
                       "Marks",
                       "Mars",
                       "Marshal",
                       "Martin",
                       "Marvin",
                       "Mason",
                       "Matthew",
                       "Max",
                       "Michael",
                       "Mickey",
                       "Mike",
                       "Nathan",
                       "Nathaniel",
                       "Neil",
                       "Nelson",
                       "Nicholas",
                       "Nick",
                       "Noah",
                       "Norman",
                       "Oliver",
                       "Oscar",
                       "Owen",
                       "Patrick",
                       "Paul",
                       "Peter",
                       "Philip",
                       "Phoebe",
                       "Quentin",
                       "Randall",
                       "Randolph",
                       "Randy",
                       "Ray",
                       "Raymond",
                       "Reed",
                       "Rex",
                       "Richard",
                       "Richie",
                       "Riley",
                       "Robert",
                       "Robin",
                       "Robinson",
                       "Rock",
                       "Roger",
                       "Ronald",
                       "Rowan",
                       "Roy",
                       "Ryan",
                       "Sam",
                       "Sammy",
                       "Samuel",
                       "Scott",
                       "Sean",
                       "Shawn",
                       "Sidney",
                       "Simon",
                       "Solomon",
                       "Spark",
                       "Spencer",
                       "Spike",
                       "Stanley",
                       "Steve",
                       "Steven",
                       "Stewart",
                       "Stuart",
                       "Terence",
                       "Terry",
                       "Ted",
                       "Thomas",
                       "Tim",
                       "Timothy",
                       "Todd",
                       "Tommy",
                       "Tom",
                       "Thomas",
                       "Tony",
                       "Tyler",
                       "Ultraman",
                       "Ulysses",
                       "Van",
                       "Vern",
                       "Vernon",
                       "Victor",
                       "Vincent",
                       "Warner",
                       "Warren",
                       "Wayne",
                       "Wesley",
                       "William",
                       "Willy",
                       "Zack",
                       "Zachary",
                       "Abigail",
                       "Abby",
                       "Ada",
                       "Adelaide",
                       "Adeline",
                       "Alexandra",
                       "Ailsa",
                       "Aimee",
                       "Alexis",
                       "Alice",
                       "Alicia",
                       "Alina",
                       "Allison",
                       "Alyssa",
                       "Amanda",
                       "Amy",
                       "Amber",
                       "Anastasia",
                       "Andrea",
                       "Angel",
                       "Angela",
                       "Angelia",
                       "Angelina",
                       "Ann",
                       "Anna",
                       "Anne",
                       "Annie",
                       "Anita",
                       "Ariel",
                       "April",
                       "Ashley",
                       "Audrey",
                       "Aviva",
                       "Barbara",
                       "Barbie",
                       "Beata",
                       "Beatrice",
                       "Becky",
                       "Bella",
                       "Bess",
                       "Bette",
                       "Betty",
                       "Blanche",
                       "Bonnie",
                       "Brenda",
                       "Brianna",
                       "Britney",
                       "Brittany",
                       "Camille",
                       "Candice",
                       "Candy",
                       "Carina",
                       "Carmen",
                       "Carol",
                       "Caroline",
                       "Carry",
                       "Carrie",
                       "Cassandra",
                       "Cassie",
                       "Catherine",
                       "Cathy",
                       "Chelsea",
                       "Charlene",
                       "Charlotte",
                       "Cherry",
                       "Cheryl",
                       "Chloe",
                       "Chris",
                       "Christina",
                       "Christine",
                       "Christy",
                       "Cindy",
                       "Claire",
                       "Claudia",
                       "Clement",
                       "Cloris",
                       "Connie",
                       "Constance",
                       "Cora",
                       "Corrine",
                       "Crystal",
                       "Daisy",
                       "Daphne",
                       "Darcy",
                       "Dave",
                       "Debbie",
                       "Deborah",
                       "Debra",
                       "Demi",
                       "Diana",
                       "Dolores",
                       "Donna",
                       "Dora",
                       "Doris",
                       "Edith",
                       "Editha",
                       "Elaine",
                       "Eleanor",
                       "Elizabeth",
                       "Ella",
                       "Ellen",
                       "Ellie",
                       "Emerald",
                       "Emily",
                       "Emma",
                       "Enid",
                       "Elsa",
                       "Erica",
                       "Estelle",
                       "Esther",
                       "Eudora",
                       "Eva",
                       "Eve",
                       "Evelyn",
                       "Fannie",
                       "Fay",
                       "Fiona",
                       "Flora",
                       "Florence",
                       "Frances",
                       "Frederica",
                       "Frieda",
                       "Flta",
                       "Gina",
                       "Gillian",
                       "Gladys",
                       "Gloria",
                       "Grace",
                       "Grace",
                       "Greta",
                       "Gwendolyn",
                       "Hannah",
                       "Haley",
                       "Hebe",
                       "Helena",
                       "Hellen",
                       "Henna",
                       "Heidi",
                       "Hillary",
                       "Ingrid",
                       "Isabella",
                       "Ishara",
                       "Irene",
                       "Iris",
                       "Ivy",
                       "Jacqueline",
                       "Jade",
                       "Jamie",
                       "Jane",
                       "Janet",
                       "Jasmine",
                       "Jean",
                       "Jenna",
                       "Jennifer",
                       "Jenny",
                       "Jessica",
                       "Jessie",
                       "Jill",
                       "Joan",
                       "Joanna",
                       "Jocelyn",
                       "Joliet",
                       "Josephine",
                       "Josie",
                       "Joy",
                       "Joyce",
                       "Judith",
                       "Judy",
                       "Julia",
                       "Juliana",
                       "Julie",
                       "June",
                       "Karen",
                       "Karida",
                       "Katherine",
                       "Kate",
                       "Kathy",
                       "Katie",
                       "Katrina",
                       "Kay",
                       "Kayla",
                       "Kelly",
                       "Kelsey",
                       "Kimberly",
                       "Kitty",
                       "Lareina",
                       "Lassie",
                       "Laura",
                       "Lauren",
                       "Lena",
                       "Lydia",
                       "Lillian",
                       "Lily",
                       "Linda",
                       "lindsay",
                       "Lisa",
                       "Liz",
                       "Lora",
                       "Lorraine",
                       "Louisa",
                       "Louise",
                       "Lucia",
                       "Lucy",
                       "Lucine",
                       "Lulu",
                       "Lydia",
                       "Lynn",
                       "Mabel",
                       "Madeline",
                       "Maggie",
                       "Mamie",
                       "Manda",
                       "Mandy",
                       "Margaret",
                       "Mariah",
                       "Marilyn",
                       "Martha",
                       "Mavis",
                       "Mary",
                       "Matilda",
                       "Maureen",
                       "Mavis",
                       "Maxine",
                       "May",
                       "Mayme",
                       "Megan",
                       "Melinda",
                       "Melissa",
                       "Melody",
                       "Mercedes",
                       "Meredith",
                       "Mia",
                       "Michelle",
                       "Milly",
                       "Miranda",
                       "Miriam",
                       "Miya",
                       "Molly",
                       "Monica",
                       "Morgan",
                       "Nancy",
                       "Natalie",
                       "Natasha",
                       "Nicole",
                       "Nikita",
                       "Nina",
                       "Nora",
                       "Norma",
                       "Nydia",
                       "Octavia",
                       "Olina",
                       "Olivia",
                       "Ophelia",
                       "Oprah",
                       "Pamela",
                       "Patricia",
                       "Patty",
                       "Paula",
                       "Pauline",
                       "Pearl",
                       "Peggy",
                       "Philomena",
                       "Phoebe",
                       "Phyllis",
                       "Polly",
                       "Priscilla",
                       "Quentina",
                       "Rachel",
                       "Rebecca",
                       "Regina",
                       "Rita",
                       "Rose",
                       "Roxanne",
                       "Ruth",
                       "Sabrina",
                       "Sally",
                       "Sandra",
                       "Samantha",
                       "Sami",
                       "Sandra",
                       "Sandy",
                       "Sarah",
                       "Savannah",
                       "Scarlett",
                       "Selma",
                       "Selina",
                       "Serena",
                       "Sharon",
                       "Sheila",
                       "Shelley",
                       "Sherry",
                       "Shirley",
                       "Sierra",
                       "Silvia",
                       "Sonia",
                       "Sophia",
                       "Stacy",
                       "Stella",
                       "Stephanie",
                       "Sue",
                       "Sunny",
                       "Susan",
                       "Tamara",
                       "Tammy",
                       "Tanya",
                       "Tasha",
                       "Teresa",
                       "Tess",
                       "Tiffany",
                       "Tina",
                       "Tonya",
                       "Tracy",
                       "Ursula",
                       "Vanessa",
                       "Venus",
                       "Vera",
                       "Vicky",
                       "Victoria",
                       "Violet",
                       "Virginia",
                       "Vita",
                       "Vivian")
               val index = random.nextInt(Surname.size - 1)
               var name = Surname[index] //获得一个随机的姓氏
               name = name + random.nextInt(100);
//               /* 从常用字中选取一个或两个字作为名 */
//               if (random.nextBoolean()) {
//                   name += getChinese()
//               } else {
//                   name += getChinese()
//               }
               RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().loginByPhone(Et_inPhone.text.toString(), SPUtil.getString("city").toString())).subscribe({

                   SPUtil.saveString("token", it.token)
                   SPUtil.saveString("userid",Et_inPhone.text.toString())
                   SPUtil.saveString("rongyun_token",it.rongyun_token)
                   MyApplication.getInstance().setGetAlisa()
                   RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().editDataNoHead(SPUtil.getString("userid"),name,"女","","")).subscribe({
//                       ToastUtils.showLong("保存成功")
                       startActivity(MainActivity::class.java)
                       finishWithAlpha()
                   },{
                       ToastUtils.showLong(it.message)
                   })

               }, {
                   ToastUtils.showLong(it.message)
               })
           }
        }
    }
    override fun loadViewLayout() {
        setContentView(R.layout.activity_register)
        initTitle("注册", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })


    }

    fun getChinese(): String? {
        var str: String? = null
        val highPos: Int
        val lowPos: Int
        var random = Random()
        highPos = 176 + Math.abs(random.nextInt(71))//区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        random = Random()
        lowPos = 161 + Math.abs(random.nextInt(94))//位码，0xA0打头，范围第1~94列

        val bArr = ByteArray(2)
        bArr[0] = highPos.toByte()
        bArr[1] = lowPos.toByte()
        try {
            str = String(bArr)    //区位码组合成汉字
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return str
    }

    override fun findViewById() {
        mCountDownTimerUtils = CountDownTimerUtils(Tv_getCode, 60000, 1000)
        Tv_getCode.setOnClickListener{
            if (Et_inPhone.text.toString().equals("")){
                ToastUtils.showLong("电话号不能为空");
            }else {
                sendCode("86", Et_inPhone.text.toString())
            }
        }
        Tv_agreement.setOnClickListener {
            WebActivity.startSelf(this, "用户协议", "http://app.heixiuapp.cn:8080/data/register.htm")
        }
        Bt_signIn.setOnClickListener {
            if(Et_inPass.length()<6){
                ToastUtils.showLong("密码太短");
                return@setOnClickListener
            }
            if(Et_inPass.text.toString().equals("")||Et_inPhone.text.toString().equals("")||Tv_inCode.text.toString().equals("")){
                ToastUtils.showLong("不能为空");
            }else {
                submitCode("86", Et_inPhone.text.toString(), Tv_inCode.text.toString())
            }
        }
    }

    override fun setListener() {

    }

    override fun processLogic() {

    }
    fun sendCode(country: String, phone: String) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override  fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    mCountDownTimerUtils!!.start()
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        })
        // 触发操作
        SMSSDK.getVerificationCode(country, phone)
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    fun submitCode(country: String, phone: String, code: String) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().register(phone,((Math.random()*9+1)*10000).toString(),Et_inPass.text.toString()))
                            .subscribe({
                        var message:Message = Message()
                        message.obj = "注册成功"
                        handler.sendMessage(message);
                    },{
                        ToastUtils.showLong(it.message)
//                        var message:Message = Message()
//                        message.obj = it.message
//                        handler.sendMessage(message);
                    })

                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        })
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code)
    }
    override fun onDestroy() {
        super.onDestroy()
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler()
    };
}
