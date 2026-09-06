import axios from "axios";

const API = "http://localhost:8080/ai";

class AiService {

    summarize(article) {

        return axios.post(

            `${API}/summarize`,

            article,

            {
                headers: {
                    "Content-Type": "text/plain"
                }
            }

        );

    }
    sentiment(article){

    return axios.post(

        `${API}/sentiment`,

        article,

        {
            headers:{
                "Content-Type":"text/plain"
            }
        }

    );
}
keywords(article){

    return axios.post(

        `${API}/keywords`,

        article,

        {

            headers:{
                "Content-Type":"text/plain"
            }

        }

    );

}

}

export default new AiService();