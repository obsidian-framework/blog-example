package fr.kainovaii.obsidian.app.domain.post;

import fr.kainovaii.obsidian.app.domain.user.UserRepository;
import fr.kainovaii.obsidian.database.DB;
import fr.kainovaii.obsidian.database.seeder.annotations.Seeder;

@Seeder(priority = 10)
public class PostSeeder
{
    public static void seed()
    {
        PostRepository postRepository = new PostRepository();

        DB.withConnection(() -> {
            if (postRepository.findAll().isEmpty()) {
                postRepository.create("fore-magna-nostris-et-propinqua", "Fore magna nostris et propinqua.", "Ultima Syriarum est Palaestina per intervalla magna protenta, cultis abundans terris et nitidis et civitates habens quasdam egregias, nullam nulli cedentem sed sibi vicissim velut ad perpendiculum aemulas: Caesaream, quam ad honorem Octaviani principis exaedificavit Herodes, et Eleutheropolim et Neapolim itidemque Ascalonem Gazam aevo superiore exstructas.", "admin");
                postRepository.create("aliquotiens-indicium-specie-obscurum-quoque", "Aliquotiens indicium specie obscurum quoque.", "Quaestione igitur per multiplices dilatata fortunas cum ambigerentur quaedam, non nulla levius actitata constaret, post multorum clades Apollinares ambo pater et filius in exilium acti cum ad locum Crateras nomine pervenissent, villam scilicet suam quae ab Antiochia vicensimo et quarto disiungitur lapide, ut mandatum est, fractis cruribus occiduntur.", "admin");
                postRepository.create("quaestor-pittacas-non-increpabat-sed", "Quaestor Pittacas non increpabat sed.", "Quapropter a natura mihi videtur potius quam ab indigentia orta amicitia, applicatione magis animi cum quodam sensu amandi quam cogitatione quantum illa res utilitatis esset habitura. Quod quidem quale sit, etiam in bestiis quibusdam animadverti potest, quae ex se natos ita amant ad quoddam tempus et ab eis ita amantur ut facile earum sensus appareat. Quod in homine multo est evidentius, primum ex ea caritate quae est inter natos et parentes, quae dirimi nisi detestabili scelere non potest; deinde cum similis sensus exstitit amoris, si aliquem nacti sumus cuius cum moribus et natura congruamus, quod in eo quasi lumen aliquod probitatis et virtutis perspicere videamur.", "admin");
            }
            return null;
        });
    }
}
