package fit.d6.candy.dsl.test

import fit.d6.candy.bukkit.dsl.Compound
import fit.d6.candy.bukkit.dsl.List

fun nbtTest() {
    Compound {

        "type" + "compound"

        "objects" + Compound {

            "names" + List {

                +"DeeChael"
                +"Qianm_ing"

            }

            "enable" + true

        }

    }
}