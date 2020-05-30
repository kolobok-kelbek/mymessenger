import fetch from 'node-fetch'
import {createHttpLink} from 'apollo-link-http'
import {InMemoryCache} from 'apollo-cache-inmemory'
import ApolloClient from 'apollo-client'
import {useQuery} from "@apollo/react-hooks";
import gql from 'graphql-tag';
import {ApolloProvider} from '@apollo/react-hooks';

const QUERY = gql`
    query {
        allLinks {
            url,
            postedBy {
                name
            }
        }
    }
`;

const client = new ApolloClient({
  link: createHttpLink({
    uri: 'http://mymessenger.local/graphql',
    fetch: fetch,
  }),
  cache: new InMemoryCache(),
});

function Welcome(props) {
  const {data} = useQuery(QUERY, {});
  console.log(data);
  return <h1>Привет, {props.name}</h1>;
}

function Home() {
  return <ApolloProvider client={client}>
    <Welcome name="Илья"/>
  </ApolloProvider>
}

export default Home
